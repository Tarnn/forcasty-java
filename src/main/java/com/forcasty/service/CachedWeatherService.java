package com.forcasty.service;

import com.forcasty.dto.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachedWeatherService {
    
    private static final Logger logger = LoggerFactory.getLogger(CachedWeatherService.class);
    private final WeatherService weatherService;
    private final CacheManager cacheManager;
    
    @Autowired
    public CachedWeatherService(WeatherService weatherService, CacheManager cacheManager) {
        this.weatherService = weatherService;
        this.cacheManager = cacheManager;
    }
    
    public WeatherResponse getWeatherForecast(String address) {
        logger.debug("Checking cache for address: {}", address);
        
        Cache cache = cacheManager.getCache("weatherCache");
        
        // Try to get from cache first
        if (cache != null) {
            Cache.ValueWrapper cached = cache.get(address);
            if (cached != null) {
                logger.info("Cache hit for address: {}", address);
                WeatherResponse response = (WeatherResponse) cached.get();
                response.setFromCache(true);
                return response;
            }
        }
        
        // Cache miss - fetch from API
        logger.info("Cache miss - fetching from API for address: {}", address);
        WeatherResponse response = weatherService.getWeatherForecast(address);
        response.setFromCache(false);
        
        // Store in cache
        if (cache != null) {
            cache.put(address, response);
        }
        
        logger.debug("Weather response fetched and cached for address: {}", address);
        return response;
    }
}
