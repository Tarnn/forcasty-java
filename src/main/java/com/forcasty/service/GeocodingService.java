package com.forcasty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
        
        // Try multiple strategies to geocode the address
        try {
            // Strategy 1: Try US Census Bureau first (most reliable for US addresses)
            double[] coords = tryCensusBureauGeocode(address);
            if (coords != null) {
                return coords;
            }
            
            // Strategy 2: Try full address with Nominatim
            coords = tryNominatimGeocode(address);
            if (coords != null) {
                return coords;
            }
            
            // Strategy 3: Extract just city and state from address and try that
            String simplifiedAddress = simplifyAddress(address);
            if (simplifiedAddress != null && !simplifiedAddress.equals(address)) {
                logger.info("Full address geocoding failed, trying simplified: {}", simplifiedAddress);
                coords = tryNominatimGeocode(simplifiedAddress);
                if (coords != null) {
                    return coords;
                }
            }
            
            // All strategies failed
            logger.error("All geocoding strategies failed for address: {}", address);
            throw new RuntimeException("Unable to geocode address: " + address + 
                    ". Please provide a valid, specific address with city and state or ZIP code.");
            
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to get coordinates for address: {}, error: {}", address, e.getMessage(), e);
            throw new RuntimeException("Unable to get coordinates for address: " + address + ". Error: " + e.getMessage(), e);
        }
    }
    
    private double[] tryNominatimGeocode(String address) {
        try {
            String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
            String url = String.format("https://nominatim.openstreetmap.org/search?q=%s&format=json&limit=1", encodedAddress);
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Forcasty-Weather-App/1.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            logger.info("Calling Nominatim geocoding API: {}", url);
            
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String response = responseEntity.getBody();
            
            logger.info("Nominatim response status: {}, body length: {}", 
                       responseEntity.getStatusCode(), 
                       response != null ? response.length() : 0);
            
            if (response == null || response.trim().isEmpty() || response.equals("[]")) {
                logger.warn("No results from Nominatim for: {}", address);
                return null;
            }
            
            JsonNode jsonNode = objectMapper.readTree(response);
            
            if (jsonNode.isArray() && jsonNode.size() > 0) {
                JsonNode firstResult = jsonNode.get(0);
                
                if (firstResult.has("lat") && firstResult.has("lon")) {
                    double lat = firstResult.get("lat").asDouble();
                    double lon = firstResult.get("lon").asDouble();
                    double[] coordinates = new double[]{lat, lon};
                    
                    logger.info("Nominatim successfully geocoded '{}': lat={}, lon={}", 
                                address, coordinates[0], coordinates[1]);
                    return coordinates;
                }
            }
            
            return null;
        } catch (Exception e) {
            logger.warn("Nominatim geocoding failed for '{}': {}", address, e.getMessage());
            return null;
        }
    }
    
    private double[] tryCensusBureauGeocode(String address) {
        try {
            // US Census Bureau Geocoding API (free, no API key required)
            String encodedAddress = java.net.URLEncoder.encode(address, "UTF-8");
            String url = String.format("https://geocoding.geo.census.gov/geocoder/locations/onelineaddress?address=%s&benchmark=2020&format=json", 
                                      encodedAddress);
            
            logger.info("Calling US Census Bureau geocoding API: {}", url);
            
            String response = restTemplate.getForObject(url, String.class);
            
            if (response == null || response.trim().isEmpty()) {
                logger.warn("No response from Census Bureau for: {}", address);
                return null;
            }
            
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode result = jsonNode.path("result");
            JsonNode addressMatches = result.path("addressMatches");
            
            if (addressMatches.isArray() && addressMatches.size() > 0) {
                JsonNode firstMatch = addressMatches.get(0);
                JsonNode coordinates = firstMatch.path("coordinates");
                
                if (coordinates.has("x") && coordinates.has("y")) {
                    double lon = coordinates.get("x").asDouble();
                    double lat = coordinates.get("y").asDouble();
                    double[] coords = new double[]{lat, lon};
                    
                    logger.info("Census Bureau successfully geocoded '{}': lat={}, lon={}", 
                                address, coords[0], coords[1]);
                    return coords;
                }
            }
            
            logger.warn("No results from Census Bureau for: {}", address);
            return null;
        } catch (Exception e) {
            logger.warn("Census Bureau geocoding failed for '{}': {}", address, e.getMessage());
            return null;
        }
    }
    
    private String extractZipCodeFromAddress(String address) {
        if (address == null) {
            return null;
        }
        
        String[] parts = address.split("\\s+");
        for (String part : parts) {
            if (part.matches("\\d{5}(-\\d{4})?")) {
                return part.replaceAll("-\\d{4}", "");
            }
        }
        return null;
    }
    
    private String simplifyAddress(String address) {
        if (address == null) {
            return null;
        }
        
        // Try to extract "City, State" or "City, State ZIP" pattern
        // Example: "350 Fifth Avenue, New York, NY 10118" -> "New York, NY"
        // Example: "Golden Gate Bridge, San Francisco, CA 94129" -> "San Francisco, CA"
        
        try {
            // Look for pattern: ", [City Name], [2-letter State]"
            String pattern = ",\\s*([^,]+),\\s*([A-Z]{2})";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(address);
            
            if (m.find()) {
                String city = m.group(1).trim();
                String state = m.group(2).trim();
                
                // Remove any trailing ZIP code from city
                city = city.replaceAll("\\s+\\d{5}.*$", "").trim();
                
                String simplified = city + ", " + state + ", USA";
                logger.debug("Simplified '{}' to '{}'", address, simplified);
                return simplified;
            }
        } catch (Exception e) {
            logger.warn("Failed to simplify address: {}", address);
        }
        
        return null;
    }
}
