package com.appsbyram.oscon2018.tests;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(
    value = {
        GlobalProperties.class
    }
)
@Configuration
public class AppConfig {


}
