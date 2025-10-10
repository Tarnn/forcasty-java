package com.forcasty.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import com.forcasty.service.GeocodingService;
import com.forcasty.service.WeatherService;
import com.forcasty.service.CachedWeatherService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

@TestConfiguration
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public GeocodingService testGeocodingService() {
        return new GeocodingService();
    }

    @Bean
    @Primary
    public WeatherService testWeatherService() {
        return new WeatherService(testGeocodingService());
    }

    @Bean
    @Primary
    public CachedWeatherService testCachedWeatherService() {
        return new CachedWeatherService(testWeatherService(), testCacheManager());
    }

    @Bean
    @Primary
    public CacheManager testCacheManager() {
        return new NoOpCacheManager();
    }
}
