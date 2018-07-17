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

7. Right click on the main package in your project, add new package and name it "domain"

8. Create data objects.
    
    8.1. Right click on the domain package and add a new class and name it StoreHour.java and add below snippet of code into the class.

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

    8.2. Right click on the domain package and add a new class and name it StoreLocation.java and add below snippet of code into the class.

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

    8.3. Right click on the domain package and add a new class named StoreRecord and add below snippet of code into the class.

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
    
    8.4. Right click on the domain package and add a new class named ESHits and add below snippet of code into the class.

    ```
    public int total;
    public float max_score;
    public ArrayList<ESResultHit> hits;
    ```

    8.5. Right click on the domain package and add a new class named ESREsultHit and add below snippet of code into the class.

    ```
    public String _index;
    public String _type;
    public String _id;
    public float _score;
    public StoreRecord _source;
    ```

    8.6. Right click on the domain package and add a new class named ESShard and add below snippet of code into the class

    ```
    public int total;
    public int successful;
    public int failed;
    ```

    8.7. Right click on the domain package and add a new class named ESResult and add below snippet of code into the class

    ```
    public int took;
    public boolean timed_out;
    public ESShard _shards;
    public ESHits hits;
    ```

9. Create the "StoreRepository" interface 

    9.1. Right click on the domain package and add a new interface named StoreRepository and add below snippet of code into the interface

    ```
    void createIndex(String indexName);
    void deleteIndex(String indexName);
    void addStoreToIndex(String indexName, StoreRecord store);
    List<StoreRecord> query(BigDecimal lat, BigDecimal lon, String distance);
    ```

10. Implement the "StoreRepository" interface

    10.1. Right click on package domain and add a new class named StoreRepositoryImpl and add below snippet of code

    ```

    ```


