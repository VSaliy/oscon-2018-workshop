package com.oscon2018.tutorials.domain;

import com.oscon2018.tutorials.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
