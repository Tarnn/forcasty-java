package com.forcasty.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response object")
public class ErrorResponse {
    
    @Schema(description = "Error message describing what went wrong", example = "Unable to geocode address")
    private String error;
    
    @Schema(description = "Detailed error message", example = "Unable to geocode address: Golden Gate Bridge, San Francisco, CA 94129. Please provide a valid, specific address with city and state or ZIP code.")
    private String message;
    
    @Schema(description = "HTTP status code", example = "400")
    private int status;
    
    @Schema(description = "Timestamp when error occurred", example = "1703123456789")
    private long timestamp;
    
    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public ErrorResponse(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

