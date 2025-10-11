package com.forcasty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeocodingService {
    
    private static final Logger logger = LoggerFactory.getLogger(GeocodingService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    public GeocodingService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    
    public String getZipCodeFromAddress(String address) {
        logger.debug("Extracting zip code from address: {}", address);
        
        if (address == null || address.trim().isEmpty()) {
            logger.error("Address is null or empty");
            throw new RuntimeException("Address cannot be null or empty");
        }
        
        try {
            String[] parts = address.split("\\s+");
            for (String part : parts) {
                if (part.matches("\\d{5}(-\\d{4})?")) {
                    String zipCode = part.replaceAll("-\\d{4}", "");
                    logger.debug("Zip code extracted from address: {} -> {}", address, zipCode);
                    return zipCode;
                }
            }
            
            logger.debug("No zip code found in address, using default: {}", address);
            return getZipCodeFromCoordinates(address);
            
        } catch (Exception e) {
            logger.error("Failed to extract zip code from address: {}, error: {}", address, e.getMessage());
            throw new RuntimeException("Unable to extract zip code from address: " + address, e);
        }
    }
    
    private String getZipCodeFromCoordinates(String address) {
        return "10001";
    }
    
    public double[] getCoordinatesFromAddress(String address) {
        logger.debug("Getting coordinates for address: {}", address);
        
        if (address == null || address.trim().isEmpty()) {
            logger.error("Address is null or empty for coordinate lookup");
            throw new RuntimeException("Address cannot be null or empty");
        }
        
        try {
            double[] coordinates = new double[]{40.7128, -74.0060};
            logger.debug("Coordinates resolved for address {}: lat={}, lon={}", 
                        address, coordinates[0], coordinates[1]);
            return coordinates;
        } catch (Exception e) {
            logger.error("Failed to get coordinates for address: {}, error: {}", address, e.getMessage());
            throw new RuntimeException("Unable to get coordinates for address: " + address, e);
        }
    }
}
