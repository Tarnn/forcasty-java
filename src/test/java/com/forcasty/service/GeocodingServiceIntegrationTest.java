package com.forcasty.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GeocodingServiceIntegrationTest {

    @Autowired
    private GeocodingService geocodingService;

    @Test
    void testGetZipCodeFromAddress_WithValidZipCode() {
        // Arrange
        String address = "123 Main St, New York, NY 10001";

        // Act
        String result = geocodingService.getZipCodeFromAddress(address);

        // Assert
        assertEquals("10001", result);
    }

    @Test
    void testGetZipCodeFromAddress_WithZipCodePlusFour() {
        // Arrange
        String address = "123 Main St, New York, NY 10001-1234";

        // Act
        String result = geocodingService.getZipCodeFromAddress(address);

        // Assert
        assertEquals("10001", result);
    }

    @Test
    void testGetZipCodeFromAddress_WithoutZipCode() {
        // Arrange
        String address = "123 Main St, New York, NY";

        // Act
        String result = geocodingService.getZipCodeFromAddress(address);

        // Assert
        assertEquals("10001", result);
    }

    @Test
    void testGetZipCodeFromAddress_WithEmptyAddress() {
        // Arrange
        String address = "";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            geocodingService.getZipCodeFromAddress(address);
        });
        
        assertTrue(exception.getMessage().contains("Address cannot be null or empty"));
    }

    @Test
    void testGetZipCodeFromAddress_WithNullAddress() {
        // Arrange
        String address = null;

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            geocodingService.getZipCodeFromAddress(address);
        });
        
        assertTrue(exception.getMessage().contains("Address cannot be null or empty"));
    }

    @Test
    void testGetCoordinatesFromAddress_ValidAddress() {
        // Arrange
        String address = "123 Main St, New York, NY 10001";

        // Act
        double[] result = geocodingService.getCoordinatesFromAddress(address);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.length);
        // Verify coordinates are in the NYC area (latitude ~40.6-40.9, longitude ~-74.0 to -73.9)
        // Using reasonable tolerance since this is an integration test with real API
        assertTrue(result[0] > 40.5 && result[0] < 41.0, "Latitude should be in NYC area: " + result[0]);
        assertTrue(result[1] > -74.1 && result[1] < -73.8, "Longitude should be in NYC area: " + result[1]);
    }

    @Test
    void testGetCoordinatesFromAddress_EmptyAddress() {
        // Arrange
        String address = "";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            geocodingService.getCoordinatesFromAddress(address);
        });
        
        assertTrue(exception.getMessage().contains("Address cannot be null or empty"));
    }

    @Test
    void testGetCoordinatesFromAddress_NullAddress() {
        // Arrange
        String address = null;

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            geocodingService.getCoordinatesFromAddress(address);
        });
        
        assertTrue(exception.getMessage().contains("Address cannot be null or empty"));
    }
}
