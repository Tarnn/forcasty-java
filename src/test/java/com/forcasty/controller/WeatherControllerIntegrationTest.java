package com.forcasty.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class WeatherControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Test
    void testHealthCheck() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        mockMvc.perform(get("/api/weather/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Weather API is running"));
    }

    @Test
    void testGetWeatherForecast_WithValidRequest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/weather/forecast")
                .param("address", "123 Main St, New York, NY 10001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").exists())
                .andExpect(jsonPath("$.currentTemperature").exists())
                .andExpect(jsonPath("$.temperatureUnit").exists())
                .andExpect(jsonPath("$.fromCache").exists())
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetWeatherForecast_WithEmptyAddress() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/weather/forecast")
                .param("address", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetWeatherForecast_WithMissingAddress() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/weather/forecast"))
                .andExpect(status().isBadRequest());
    }
}
