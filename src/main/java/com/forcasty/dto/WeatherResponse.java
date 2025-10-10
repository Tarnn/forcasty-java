package com.forcasty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object containing weather forecast information")
public class WeatherResponse {
    
    @Schema(description = "The address that was used for the weather lookup", example = "123 Main St, New York, NY 10001")
    private String address;
    
    @Schema(description = "Extracted zip code from the address", example = "10001")
    private String zipCode;
    
    @Schema(description = "Current temperature", example = "72.5")
    private double currentTemperature;
    
    @Schema(description = "Temperature unit", example = "°F")
    private String temperatureUnit;
    
    @Schema(description = "High temperature for the day", example = "78.0")
    private Double highTemperature;
    
    @Schema(description = "Low temperature for the day", example = "65.0")
    private Double lowTemperature;
    
    @Schema(description = "Weather condition description", example = "Clear sky")
    private String description;
    
    @Schema(description = "Indicates if the result was retrieved from cache", example = "false")
    private boolean fromCache;
    
    @Schema(description = "Timestamp when the response was generated", example = "1703123456789")
    private long timestamp;
    
    public WeatherResponse() {}
    
    public WeatherResponse(String address, String zipCode, double currentTemperature, 
                          String temperatureUnit, boolean fromCache) {
        this.address = address;
        this.zipCode = zipCode;
        this.currentTemperature = currentTemperature;
        this.temperatureUnit = temperatureUnit;
        this.fromCache = fromCache;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public double getCurrentTemperature() {
        return currentTemperature;
    }
    
    public void setCurrentTemperature(double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }
    
    public String getTemperatureUnit() {
        return temperatureUnit;
    }
    
    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }
    
    public Double getHighTemperature() {
        return highTemperature;
    }
    
    public void setHighTemperature(Double highTemperature) {
        this.highTemperature = highTemperature;
    }
    
    public Double getLowTemperature() {
        return lowTemperature;
    }
    
    public void setLowTemperature(Double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isFromCache() {
        return fromCache;
    }
    
    public void setFromCache(boolean fromCache) {
        this.fromCache = fromCache;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
