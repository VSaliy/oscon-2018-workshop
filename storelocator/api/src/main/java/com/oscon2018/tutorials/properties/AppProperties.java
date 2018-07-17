package com.oscon2018.tutorials.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
