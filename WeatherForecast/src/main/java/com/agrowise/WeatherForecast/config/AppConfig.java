package com.agrowise.WeatherForecast.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Application configuration class for defining beans.
 */
@Configuration
public class AppConfig {

    /**
     * Creates and returns a RestTemplate bean used for HTTP requests.
     *
     * @return a RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
