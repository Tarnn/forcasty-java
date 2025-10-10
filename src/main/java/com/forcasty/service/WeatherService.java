package com.forcasty.service;

import com.forcasty.dto.OpenMeteoResponse;
import com.forcasty.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    
    private final RestTemplate restTemplate;
    private final GeocodingService geocodingService;
    
    @Value("${weather.api.base-url}")
    private String weatherApiBaseUrl;
    
    public WeatherService(GeocodingService geocodingService) {
        this.restTemplate = new RestTemplate();
        this.geocodingService = geocodingService;
    }
    
    @Cacheable(value = "weatherCache", key = "#address")
    public WeatherResponse getWeatherForecast(String address) {
        try {
            double[] coordinates = geocodingService.getCoordinatesFromAddress(address);
            String zipCode = geocodingService.getZipCodeFromAddress(address);
            
            String url = String.format("%s?latitude=%.4f&longitude=%.4f&current_weather=true&daily=temperature_2m_max,temperature_2m_min&temperature_unit=fahrenheit",
                    weatherApiBaseUrl, coordinates[0], coordinates[1]);
            
            OpenMeteoResponse response = restTemplate.getForObject(url, OpenMeteoResponse.class);
            
            if (response == null || response.getCurrent_weather() == null) {
                throw new RuntimeException("No weather data available for the given address");
            }
            
            WeatherResponse weatherResponse = new WeatherResponse(
                    address,
                    zipCode,
                    response.getCurrent_weather().getTemperature(),
                    "°F",
                    false
            );
            if (response.getDaily() != null && 
                response.getDaily().getTemperature_2m_max() != null && 
                !response.getDaily().getTemperature_2m_max().isEmpty()) {
                weatherResponse.setHighTemperature(response.getDaily().getTemperature_2m_max().get(0));
            }
            
            if (response.getDaily() != null && 
                response.getDaily().getTemperature_2m_min() != null && 
                !response.getDaily().getTemperature_2m_min().isEmpty()) {
                weatherResponse.setLowTemperature(response.getDaily().getTemperature_2m_min().get(0));
            }
            
            weatherResponse.setDescription(getWeatherDescription(response.getCurrent_weather().getWeathercode()));
            
            return weatherResponse;
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage(), e);
        }
    }
    
    public WeatherResponse getCachedWeatherForecast(String address) {
        return getWeatherForecast(address);
    }
    
    String getWeatherDescription(int weatherCode) {
        switch (weatherCode) {
            case 0: return "Clear sky";
            case 1: case 2: case 3: return "Mainly clear, partly cloudy, and overcast";
            case 45: case 48: return "Fog and depositing rime fog";
            case 51: case 53: case 55: return "Drizzle: Light, moderate, and dense intensity";
            case 56: case 57: return "Freezing Drizzle: Light and dense intensity";
            case 61: case 63: case 65: return "Rain: Slight, moderate and heavy intensity";
            case 66: case 67: return "Freezing Rain: Light and heavy intensity";
            case 71: case 73: case 75: return "Snow fall: Slight, moderate, and heavy intensity";
            case 77: return "Snow grains";
            case 80: case 81: case 82: return "Rain showers: Slight, moderate, and violent";
            case 85: case 86: return "Snow showers slight and heavy";
            case 95: return "Thunderstorm: Slight or moderate";
            case 96: case 99: return "Thunderstorm with slight and heavy hail";
            default: return "Unknown weather condition";
        }
    }
}
