package com.forcasty.service;

import com.forcasty.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
public class CachedWeatherService {
    
    private final WeatherService weatherService;
    private final CacheManager cacheManager;
    
    @Autowired
    public CachedWeatherService(WeatherService weatherService, CacheManager cacheManager) {
        this.weatherService = weatherService;
        this.cacheManager = cacheManager;
    }
    
    public WeatherResponse getWeatherForecast(String address) {
        Cache cache = cacheManager.getCache("weatherCache");
        boolean fromCache = false;
        
        if (cache != null && cache.get(address) != null) {
            fromCache = true;
        }
        
        WeatherResponse response = weatherService.getWeatherForecast(address);
        response.setFromCache(fromCache);
        
        return response;
    }
}
