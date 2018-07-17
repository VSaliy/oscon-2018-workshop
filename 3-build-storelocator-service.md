# Build storelocator API
In this lab you will implement admin operations such as create and delete index, index a collection of store data posted in a csv file as well as query store based on latitude/longitude value passed in request.

1. Update dependencies in pom.xml
Add snippet below to dependencies

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>

<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.5</version>
</dependency>
```

remove starter tomcat from dependencies

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
</dependency>
```

add dependency management as shown below, this is required since we are using Spring Cloud projects.

```
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Be sure to add spring-cloud-version to properties as shown below

```
<spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
```

2. Add bootstrap.yaml file under resources and paste contents below into it

```
spring:
  cloud:
    config:
      failFast: true
      enabled: true
  application:
    name: storelocator

logging:
  level:
    org.springframework.web: 'DEBUG'

eureka:
  client:
    fetch-registry: true
    register-with-eureka: true
    registry-fetch-interval-seconds: 30
    service-url:
      defaultZone: http://localhost:8761/eureka/

server:
  port: 8081

debug: true

---
spring:
  profiles: docker
  cloud:
    config:
      uri: http://configserver:8888
eureka:
  client:
    service-url:
      defaultZone: http://eurekaserver:8761/eureka/
---
spring:
  profiles: cloud
  cloud:
    config:
      uri: ${CONFIGSERVER_URL}
eureka:
  client:
    service-url:
      defaultZone: ${REGISTRY_URL}
```

3. Add a configuration file "storelocator.yaml" to config repo and paste contents below into it.

```
springfox:
  documentation:
    swagger.v2.path: /api-docs

server:
  contextPath: /storelocator/1.0
  port: 8080

spring:
  jackson:
    date-format: com.oscon2018.tutorials.RFC3339DateFormat
    serialization:
      WRITE_DATES_AS_TIMESTAMPS: false
      
```

4. Add Dockerfile to the root of the project and paste contents below into it.

```
FROM maven:3.5-jdk-8 as builder

WORKDIR /projects/java/storelocator

COPY . .

RUN mvn clean install -DskipTests

FROM openjdk:alpine

VOLUME /tmp

COPY entrypoint.sh /

COPY --from=builder /projects/java/storelocator/target/*.jar app.jar

RUN apk update && apk upgrade && apk add --no-cache bash wget && rm -rf /var/cache/apk/* \
    && bash -c 'touch /app.jar' \
    && chmod 700 /entrypoint.sh

ENTRYPOINT ["./entrypoint.sh"]

CMD [ "start" ]
```

5. Add an entrypoint.sh script to the root of the project and paste contents below to it.

```
#!/usr/bin/env bash

CMD=$1
echo "Command :" $CMD

JAVA_OPTS="$JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses -Duser.timezone=America/Los_Angeles -Dspring.profiles.active=default,$PROFILE -Djava.security.egd=file:/dev/./urandom"

echo $JAVA_OPTS

case "$CMD" in
    "start")
        echo "Starting SpringBoot application"
        exec java $JAVA_OPTS -jar /app.jar
    ;;

    * )
    # custom command
    echo "custom command"
    exec $CMD ${@:2}
    ;;
esac

```

6. Add Makefile to the root of the project and paste contents below to it

```
TAG?=latest

build:
	docker build -t appsbyram/storelocator:$(TAG) .

push:
	docker push appsbyram/storelocator:$(TAG)

```

7. Add a properties class to hold the configuration information

    7.1. Right click on the main package in your project, add new package and name it properties.

    7.2. Add a new class under properties package and name it AppProperties and paste the contents below into it.

    ```
    @ConfigurationProperties(prefix = "application")
    public class AppProperties {
        private String elasticUrl;

        public String getElasticUrl() {
            return elasticUrl;
        }

        public void setElasticUrl(String elasticUrl) {
            this.elasticUrl = elasticUrl;
        }
    }
    ```

    7.3. Add an AppConfig class to configuration package and add the below snippet into it.

    ```
    @EnableConfigurationProperties(
        value = {
                AppProperties.class
        }
    )
    @Configuration
    public class AppConfig {
        
    }
    ```
8. Add below snippet to storelocator config file

```
application:
  elasticUrl: http://localhost:9200

spring:
  profiles: docker
application:
  elasticUrl: http://elasticsearch:9200
```

9. Right click on the main package in your project, add new package and name it "domain"

10. Create data objects.
    
    10.1. Right click on the domain package and add a new class and name it StoreHour.java and add below snippet of code into the class.

    ``` 
    private String dayOfWeek;
    private String openTime;
    private String closeTime;


    public StoreHour(Builder builder) {
        this.dayOfWeek = builder.dayOfWeek;
        this.openTime = builder.openTime;
        this.closeTime = builder.closeTime;
    }

    public static final class Builder {
        private String dayOfWeek;
        private String openTime;
        private String closeTime;

        public Builder dayOfWeek(String value) {
            this.dayOfWeek = value;
            return this;
        }
        
        public Builder openTime(String value) {
            this.openTime = value;
            return this;
        }
        
        public Builder closeTime(String value) {
            this.closeTime = value;
            return this;
        }
        
        public StoreHour build() {
            return new StoreHour(this);
        }
    }
    ```

    10.2. Right click on the domain package and add a new class and name it StoreLocation.java and add below snippet of code into the class.

    ```
    private BigDecimal lat;
    private BigDecimal lon;

    public StoreLocation(Builder builder) {
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public static final class Builder {
        private BigDecimal lat;
        private BigDecimal lon;

        public Builder lat(BigDecimal lat) {
            this.lat = lat;
            return this;
        }

        public Builder lon(BigDecimal lon) {
            this.lon = lon;
            return this;
        }

        public StoreLocation build() {
            return new StoreLocation(this);
        }
    }    
    ```

    10.3. Right click on the domain package and add a new class named StoreRecord and add below snippet of code into the class.

    ```
    private String storeCode;
    private String businessName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String primaryPhone;
    private String website;
    private String description;
    private String paymentTypes[];
    private String primaryCategory;
    private String photo;
    private ArrayList<StoreHour> storeHours;
    private StoreLocation storeLocation;
    private String sapId;

    public StoreRecord(Builder builder) {
        this.storeCode = builder.storeCode;
        this.businessName = builder.businessName;
        this.address1 = builder.address1;
        this.address2 = builder.address2;
        this.city = builder.city;
        this.state = builder.state;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.primaryPhone = builder.primaryPhone;
        this.website = builder.website;
        this.description = builder.description;
        this.paymentTypes = builder.paymentTypes;
        this.primaryCategory = builder.primaryCategory;
        this.photo = builder.photo;
        this.storeHours = builder.storeHours;
        this.storeLocation = builder.storeLocation;
        this.sapId = builder.sapId;

    }

    public static final class Builder {
        private String storeCode;
        private String businessName;
        private String address1;
        private String address2;
        private String city;
        private String state;
        private String postalCode;
        private String country;
        private String primaryPhone;
        private String website;
        private String description;
        private String paymentTypes[];
        private String primaryCategory;
        private String photo;
        private ArrayList<StoreHour> storeHours;
        private StoreLocation storeLocation;
        private String sapId;

        public Builder storeCode(String value) {
            this.storeCode = value;
            return this;
        }

        public Builder businessName(String value) {
            this.businessName = value;
            return this;
        }

        public Builder address1(String value) {
            this.address1 = value;
            return this;
        }

        public Builder address2(String value) {
            this.address2 = value;
            return this;
        }

        public Builder city(String value) {
            this.city = value;
            return this;
        }

        public Builder state(String value) {
            this.state = value;
            return this;
        }

        public Builder postalCode(String value) {
            this.postalCode = value;
            return this;
        }

        public Builder country(String value) {
            this.country = value;
            return this;
        }

        public Builder primaryPhone(String value) {
            this.primaryPhone = value;
            return this;
        }

        public Builder website(String value) {
            this.website = value;
            return this;
        }

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder paymentTypes(String[] value) {
            this.paymentTypes = value;
            return this;
        }

        public Builder primaryCategory(String value) {
            this.primaryCategory = value;
            return this;
        }

        public Builder photo(String value){
            this.photo = value;
            return this;
        }

        public Builder storeHours(ArrayList<StoreHour> hours) {
            this.storeHours = hours;
            return this;
        }

        public Builder storeLocation(StoreLocation location) {
            this.storeLocation = location;
            return this;
        }

        public Builder sapId(String value) {
            this.sapId = value;
            return this;
        }

        public StoreRecord build() {
            return new StoreRecord(this);
        }
    }
    ```
    
    10.4. Right click on the domain package and add a new class named ESHits and add below snippet of code into the class.

    ```
    public int total;
    public float max_score;
    public ArrayList<ESResultHit> hits;
    ```

    10.5. Right click on the domain package and add a new class named ESREsultHit and add below snippet of code into the class.

    ```
    public String _index;
    public String _type;
    public String _id;
    public float _score;
    public StoreRecord _source;
    ```

    10.6. Right click on the domain package and add a new class named ESShard and add below snippet of code into the class

    ```
    public int total;
    public int successful;
    public int failed;
    ```

    10.7. Right click on the domain package and add a new class named ESResult and add below snippet of code into the class

    ```
    public int took;
    public boolean timed_out;
    public ESShard _shards;
    public ESHits hits;
    ```

11. Create the "StoreRepository" interface 

    11.1. Right click on the domain package and add a new interface named StoreRepository and add below snippet of code into the interface

    ```
    void createIndex(String indexName);
    void deleteIndex(String indexName);
    void addStoreToIndex(String indexName, StoreRecord store);
    List<StoreRecord> query(String indexName, BigDecimal lat, BigDecimal lon, String distance);
    ```

12. Implement the "StoreRepository" interface

    10.1. Right click on package domain and add a new class named ESStoreRepository and add below snippet of code

    ```
    @Repository
    public class ESStoreRepository implements StoreRepository {
        private static final Logger LOG = LoggerFactory.getLogger(StoreRepository.class);

        @Autowired
        private AppProperties properties;

        @Override
        public void createIndex(String indexName) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(createIndexPayload(),headers);

            String url = String.format(properties.getElasticUrl() + "/%s?pretty", indexName);

            RestTemplate rt = new RestTemplate();
            rt.put(url, entity);
        }

        @Override
        public void deleteIndex(String indexName) {
            String url = String.format(properties.getElasticUrl() + "/%s", indexName);
            RestTemplate rt = new RestTemplate();
            rt.delete(url);
        }

        @Override
        public void addStoreToIndex(String indexName, StoreRecord store) {
            try{
                String url = String.format(properties.getElasticUrl() + "/%s/store/%s", indexName, store.getStoreCode());
                RestTemplate rt = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<StoreRecord> entity = new HttpEntity<StoreRecord>(store,headers);

                rt.postForEntity(url, entity, StoreRecord.class);

            } catch(Exception ex){
                LOG.error("Error occurred adding store to index:", ex);
            }
        }

        @Override
        public List<StoreRecord> query(String indexName, BigDecimal lat, BigDecimal lon, Integer distance) {
            ArrayList<StoreRecord> stores = new ArrayList<>();

            try{
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<String> entity = new HttpEntity<String>(esQueryPayload(lat, lon, distance), headers);

                String url = String.format(properties.getElasticUrl(), "/%s/_search", indexName);
                RestTemplate rt = new RestTemplate();

                ESResult esResult = rt.postForObject(url, entity, ESResult.class);
                if (esResult != null && esResult.hits != null && esResult.hits.total == 0) {
                    LOG.info("No hits");
                } else {
                    for (ESResultHit oneRes : esResult.hits.hits) {
                        stores.add(oneRes._source);
                    }
                }

                return stores;

            } catch(Exception ex) {
                LOG.error("Error querying elasticsearch ", ex);
            }

            return null;
        }

        private String createIndexPayload() {
            return "{\n" +
                "\"mappings\": {\n" +
                "\"store\" : {\n" +
                    "\"_source\" : {\n" +
                    "\"enabled\" : true \n" +
                    "\"},\n" +
                    "\"properties\" : { \n" +
                    "\"location\" : { \n" +
                    "\"type\" : \"geo_point\" } \n" +
                    "}}}}";
        }

        private String esQueryPayload(BigDecimal lat, BigDecimal lon, Integer distance) {
            String retval = String.format("{\n" +
                    "\"from\": 0, \"size\": 20, \"query\" : {\n" +
                    "\t\"filtered\" : {\n" +
                    "\t\t\"query\": {\n" +
                    "\t\t\t\"match_all\": { }\n" +
                    "\t\t},\n" +
                    "\t\t\"filter\" : {\n" +
                    "\t\t    \"geo_distance\" : {\n" +
                    "\t\t\t\"distance\" : \"%dmi\",\n" +
                    "\t\t\t\"location\" : {\n" +
                    "\t\t\t\t\"lat\": %f, \"lon\": %f\n" +
                    "\t\t\t }\n" +
                    "        \t\t}\n" +
                    "    }\n" +
                    "}},\"sort\": [\n" +
                    "  {\n" +
                    "     \"_geo_distance\": {\n" +
                    "        \"location\": {\n" +
                    "          \"lat\":  %f,\n" +
                    "          \"lon\": %f\n" +
                    "        },\n" +
                    "        \"order\":         \"asc\",\n" +
                    "        \"unit\":          \"mi\",\n" +
                    "        \"mode\": \"min\",\n" +
                    "        \"distance_type\": \"plane\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]}", distance, lat, lon, lat, lon);

            return retval;
        }
    }

    ```
13. Structure Admin and Index APIs into its own packages.

14. Right click on admin package and add a new interface and name it AdminService and add snippet below to it.

```
import com.oscon2018.tutorials.models.Index;

public interface AdminService {
    void createIndex(Index index);
    void deleteIndex(Index index);
}
```

15. Right click on admin package and add a new class and implement the AdminService and add snippet below to it.

```
import com.oscon2018.tutorials.domain.StoreRepository;
import com.oscon2018.tutorials.models.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StoreRepository repository;

    @Override
    public void createIndex(Index index) {
        // Put business logic here

        repository.createIndex(index.getName());
    }

    @Override
    public void deleteIndex(Index index) {
        // Put business logic/rules here

        repository.deleteIndex(index.getName());
    }
}
```

16. Wireup operations in AdminAPI controller to AdminService. AdminAPI controller code should look like below.

```
@Controller
public class AdminApiController implements AdminApi {

    private static final Logger log = LoggerFactory.getLogger(AdminApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private AdminService service;

    @org.springframework.beans.factory.annotation.Autowired
    public AdminApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Index> createIndex(@ApiParam(value = "New index to create"  )  @Valid @RequestBody Index newIndex) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                service.createIndex(newIndex);
                return new ResponseEntity<Index>(newIndex, HttpStatus.CREATED);

            } catch (Exception e) {
                log.error("Error calling createIndex on AdminService", e);
                return new ResponseEntity<Index>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Index>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteIndex(@ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName) {
        String accept = request.getHeader("Accept");
        try{
            service.deleteIndex(indexName);

        } catch(Exception ex) {
            log.error("Error calling deleteIndex on AdminService", ex);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
```

17. Add a model builder class to convert data entities to model. See snippet below

```
import com.oscon2018.tutorials.domain.StoreLocation;
import com.oscon2018.tutorials.domain.StoreRecord;
import com.oscon2018.tutorials.models.Location;
import com.oscon2018.tutorials.models.Store;

import java.util.Arrays;

public class ModelBuilder {

    public static Store getStore(StoreRecord entity) {
        return new Store()
                .address1(entity.getAddress1())
                .address2(entity.getAddress2())
                .businessName(entity.getBusinessName())
                .storeCode(entity.getStoreCode())
                .city(entity.getCity())
                .state(entity.getState())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .primaryPhone(entity.getPrimaryPhone())
                .website(entity.getWebsite())
                .description(entity.getDescription())
                .paymentTypes(Arrays.asList(entity.getPaymentTypes()))
                .primaryCategory(entity.getPrimaryCategory())
                .photo(entity.getPhoto())
                .location(getLocation(entity.getStoreLocation()))
                .sapId(entity.getSapId());
    }

    public static Location getLocation(StoreLocation sl){
        return new Location()
                .latitude(sl.getLat())
                .longitude(sl.getLon());
    }
}

```

18. Add an entity builder class to convert model class to data entity. See snippet below

```
import com.oscon2018.tutorials.models.Location;
import com.oscon2018.tutorials.models.Store;

public class EntityBuilder {
    public static StoreRecord getStoreRecord(Store model){
        return new StoreRecord.Builder()
                .address1(model.getAddress1())
                .address2(model.getAddress2())
                .businessName(model.getBusinessName())
                .storeCode(model.getStoreCode())
                .city(model.getCity())
                .state(model.getState())
                .postalCode(model.getPostalCode())
                .country(model.getCountry())
                .primaryPhone(model.getPrimaryPhone())
                .website(model.getWebsite())
                .description(model.getDescription())
                .paymentTypes(model.getPaymentTypes().toArray(new String[0]))
                .primaryCategory(model.getPrimaryCategory())
                .photo(model.getPhoto())
                .storeLocation(getLocation(model.getLocation()))
                .sapId(model.getSapId())
                .build();
    }
    public static StoreLocation getLocation(Location model){
        return new StoreLocation()
                .latitude(model.getLatitude())
                .longitude(model.getLongitude());
    }
}
```


19. Right click on index package and add a new interface and name it IndexService and add snippet below to it.

```
import com.oscon2018.tutorials.models.IndexerResponse;
import com.oscon2018.tutorials.models.QueryRequest;
import com.oscon2018.tutorials.models.QueryResponse;

import java.io.InputStream;

public interface IndexService {
    IndexerResponse indexData(String indexName, InputStream csv);
    QueryResponse query(String indexName, QueryRequest request);
}
```

20. Right click on the index package and add a new implementation for IndexService interface and add below snippet to it.

```
import com.oscon2018.tutorials.domain.EntityBuilder;
import com.oscon2018.tutorials.domain.StoreRecord;
import com.oscon2018.tutorials.domain.StoreRepository;
import com.oscon2018.tutorials.models.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService{
    private static final Logger LOG = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private StoreRepository repository;

    @Override
    public IndexerResponse indexData(String indexName, InputStream csv){
        //PUT Business Logic/Rules validation here

        IndexerResponse response = new IndexerResponse();
        ArrayList<Store> storesIndexed = new ArrayList<>();
        ArrayList<Store> storedFailedToIndex = new ArrayList<>();

        try(Reader reader = new InputStreamReader(csv)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .parse(reader);


            for (CSVRecord record : records) {
                Store newStore = new Store()
                        .storeCode(record.get(0))
                        .businessName(record.get(1))
                        .address1(record.get(2))
                        .address2(record.get(3))
                        .city(record.get(4))
                        .state(record.get(5))
                        .postalCode(record.get(6))
                        .country(record.get(7))
                        .primaryPhone(record.get(8))
                        .website(record.get(9))
                        .description(record.get(10))
                        .primaryCategory(record.get(12))
                        .photo(record.get(13))
                        .location(getStoreLocation(record))
                        .sapId(record.get(17));

                try {
                    //convert Store model to data entity
                    StoreRecord storeEntity = EntityBuilder.getStoreRecord(newStore);
                    repository.addStoreToIndex(indexName, storeEntity);
                    storesIndexed.add(newStore);

                } catch(Exception e) {
                   storedFailedToIndex.add(newStore);
                }
            }

            //Setting response
            response.setStoresIndexed(storesIndexed);
            response.setStoresFailedToIndex(storedFailedToIndex);

        } catch (Exception e) {
            LOG.error("Indexing failed in AdminServiceImpl", e);
        }

        return response;

    }

    @Override
    public QueryResponse query(String indexName, QueryRequest request) {
        QueryResponse response = new QueryResponse();
        ArrayList<Store> stores = new ArrayList<Store>();

        List<StoreRecord> rows = repository.query(indexName, request.getLat(), request.getLon(), request.getDistance());

        for(StoreRecord row : rows) {
            stores.add(ModelBuilder.getStore(row));
        }

        response.setStores(stores);
        return response;
    }

    private Location getStoreLocation(CSVRecord record) {
        return new Location()
                .latitude(new BigDecimal(record.get(15)))
                .longitude(new BigDecimal(record.get(16)));
    }
}
```

20. Wireup IndexAPI controller to IndexService operations. IndexAPI controller should look like the snippet below

```
import com.oscon2018.tutorials.models.IndexerResponse;
import com.oscon2018.tutorials.models.QueryRequest;
import com.oscon2018.tutorials.models.QueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class IndexApiController implements IndexApi {

    @Autowired
    private IndexService service;

    private static final Logger log = LoggerFactory.getLogger(IndexApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public IndexApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<IndexerResponse> index(
            @ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,
            @ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile csvPayload) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {

            try{
                IndexerResponse response = service.indexData(indexName, csvPayload.getInputStream());
                return new ResponseEntity<IndexerResponse>(response, HttpStatus.OK);

            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<IndexerResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<IndexerResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<QueryResponse> queryStores(
            @ApiParam(value = "index name",required=true) @PathVariable("indexName") String indexName,
            @ApiParam(value = "" ,required=true )  @Valid @RequestBody QueryRequest queryRequest) {

        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                QueryResponse response = service.query(indexName, queryRequest);
                return new ResponseEntity<QueryResponse>(response, HttpStatus.OK);
            } catch (Exception e) {
                log.error("Error querying for stores", e);
                return new ResponseEntity<QueryResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<QueryResponse>(HttpStatus.NOT_IMPLEMENTED);
    }

}
```