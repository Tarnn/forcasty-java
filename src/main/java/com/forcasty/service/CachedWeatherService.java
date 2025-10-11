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
        boolean fromCache = false;
        
        if (cache != null && cache.get(address) != null) {
            fromCache = true;
            logger.info("Cache hit for address: {}", address);
        } else {
            logger.info("Cache miss for address: {}", address);
        }
        
        WeatherResponse response = weatherService.getWeatherForecast(address);
        response.setFromCache(fromCache);
        
        logger.debug("Weather response prepared for address: {}, fromCache: {}", address, fromCache);
        return response;
    }
}
