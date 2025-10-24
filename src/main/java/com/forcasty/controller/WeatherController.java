package com.forcasty.controller;

import com.forcasty.dto.ErrorResponse;
import com.forcasty.dto.WeatherResponse;
import com.forcasty.service.CachedWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
@Tag(name = "Weather Forecast", description = "API for retrieving weather forecast information")
public class WeatherController {
    
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final CachedWeatherService cachedWeatherService;
    
    @Autowired
    public WeatherController(CachedWeatherService cachedWeatherService) {
        this.cachedWeatherService = cachedWeatherService;
    }
    
    @Operation(
            summary = "Get weather forecast by address",
            description = "Retrieves current weather forecast information for a given address. " +
                         "Includes current temperature, high/low temperatures, and weather description. " +
                         "Results are cached for 30 minutes to improve performance."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather forecast retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WeatherResponse.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                            {
                                              "address": "123 Main St, New York, NY 10001",
                                              "zipCode": "10001",
                                              "currentTemperature": 72.5,
                                              "temperatureUnit": "°F",
                                              "highTemperature": 78.0,
                                              "lowTemperature": 65.0,
                                              "description": "Clear sky",
                                              "fromCache": false,
                                              "timestamp": 1703123456789
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - address parameter is required or geocoding failed",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Error Response",
                                    value = """
                                            {
                                              "error": "Failed to fetch weather data",
                                              "message": "Unable to geocode address: Golden Gate Bridge, San Francisco, CA 94129. Please provide a valid, specific address with city and state or ZIP code.",
                                              "status": 400,
                                              "timestamp": 1703123456789
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/forecast")
    public ResponseEntity<?> getWeatherForecast(
            @Parameter(description = "Address for weather lookup", required = true, example = "123 Main St, New York, NY 10001")
            @RequestParam String address) {
        logger.info("Weather forecast request received for address: {}", address);
        
        try {
            WeatherResponse response = cachedWeatherService.getWeatherForecast(address);
            logger.info("Weather forecast retrieved successfully for address: {}, fromCache: {}", 
                       address, response.isFromCache());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Failed to retrieve weather forecast for address: {}, error: {}", 
                        address, e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(
                "Failed to fetch weather data",
                e.getMessage(),
                400
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @Operation(
            summary = "Health check",
            description = "Returns the current status of the Weather API service"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "API is running and healthy",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(
                                    name = "Health Response",
                                    value = "Weather API is running"
                            )
                    )
            )
    })
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check endpoint accessed");
        return ResponseEntity.ok("Weather API is running");
    }
}
