package com.oscon2018.tutorials.domain;

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
        return new StoreLocation.Builder()
                .lat(model.getLatitude())
                .lon(model.getLongitude())
                .build();
    }
}
