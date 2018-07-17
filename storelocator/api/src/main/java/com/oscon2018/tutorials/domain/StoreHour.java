package com.oscon2018.tutorials.domain;

public class StoreHour {
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
}
