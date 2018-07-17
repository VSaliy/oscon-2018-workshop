package com.oscon2018.tutorials.domain;

import java.math.BigDecimal;

public class StoreLocation {
    private BigDecimal lat;
    private BigDecimal lon;

    public StoreLocation(Builder builder) {
        this.lat = builder.lat;
        this.lon = builder.lon;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLon() {
        return lon;
    }

    public void setLon(BigDecimal lon) {
        this.lon = lon;
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
}
