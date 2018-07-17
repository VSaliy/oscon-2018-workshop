package com.oscon2018.tutorials.configuration;

import com.oscon2018.tutorials.properties.AppProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(
        value = {
                AppProperties.class
        }
)
@Configuration
public class AppConfig {

}
