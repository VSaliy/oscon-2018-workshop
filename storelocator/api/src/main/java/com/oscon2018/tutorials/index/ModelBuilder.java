package com.oscon2018.tutorials.index;

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
