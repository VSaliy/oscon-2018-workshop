package com.oscon2018.tutorials.index;

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
