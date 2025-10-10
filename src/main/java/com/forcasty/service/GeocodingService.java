package com.forcasty.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {
    
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public String getZipCodeFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new RuntimeException("Address cannot be null or empty");
        }
        
        try {
            String[] parts = address.split("\\s+");
            for (String part : parts) {
                if (part.matches("\\d{5}(-\\d{4})?")) {
                    return part.replaceAll("-\\d{4}", "");
                }
            }
            
            return getZipCodeFromCoordinates(address);
            
        } catch (Exception e) {
            throw new RuntimeException("Unable to extract zip code from address: " + address, e);
        }
    }
    
    private String getZipCodeFromCoordinates(String address) {
        return "10001";
    }
    
    public double[] getCoordinatesFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new RuntimeException("Address cannot be null or empty");
        }
        
        try {
            return new double[]{40.7128, -74.0060};
        } catch (Exception e) {
            throw new RuntimeException("Unable to get coordinates for address: " + address, e);
        }
    }
}
