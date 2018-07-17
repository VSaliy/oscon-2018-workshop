package com.oscon2018.tutorials.domain;

import java.util.ArrayList;

public class StoreRecord {
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
}
