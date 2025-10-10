package com.forcasty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class OpenMeteoResponse {
    
    private double latitude;
    private double longitude;
    private double generationtime_ms;
    private int utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    private double elevation;
    private CurrentWeather current_weather;
    private DailyUnits daily_units;
    private Daily daily;
    
    public OpenMeteoResponse() {}
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    public double getGenerationtime_ms() {
        return generationtime_ms;
    }
    
    public void setGenerationtime_ms(double generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }
    
    public int getUtc_offset_seconds() {
        return utc_offset_seconds;
    }
    
    public void setUtc_offset_seconds(int utc_offset_seconds) {
        this.utc_offset_seconds = utc_offset_seconds;
    }
    
    public String getTimezone() {
        return timezone;
    }
    
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    
    public String getTimezone_abbreviation() {
        return timezone_abbreviation;
    }
    
    public void setTimezone_abbreviation(String timezone_abbreviation) {
        this.timezone_abbreviation = timezone_abbreviation;
    }
    
    public double getElevation() {
        return elevation;
    }
    
    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
    
    public CurrentWeather getCurrent_weather() {
        return current_weather;
    }
    
    public void setCurrent_weather(CurrentWeather current_weather) {
        this.current_weather = current_weather;
    }
    
    public DailyUnits getDaily_units() {
        return daily_units;
    }
    
    public void setDaily_units(DailyUnits daily_units) {
        this.daily_units = daily_units;
    }
    
    public Daily getDaily() {
        return daily;
    }
    
    public void setDaily(Daily daily) {
        this.daily = daily;
    }
    
    public static class CurrentWeather {
        private double temperature;
        private double windspeed;
        private double winddirection;
        private int weathercode;
        private int is_day;
        private String time;
        
        public CurrentWeather() {}
        
        public double getTemperature() {
            return temperature;
        }
        
        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
        
        public double getWindspeed() {
            return windspeed;
        }
        
        public void setWindspeed(double windspeed) {
            this.windspeed = windspeed;
        }
        
        public double getWinddirection() {
            return winddirection;
        }
        
        public void setWinddirection(double winddirection) {
            this.winddirection = winddirection;
        }
        
        public int getWeathercode() {
            return weathercode;
        }
        
        public void setWeathercode(int weathercode) {
            this.weathercode = weathercode;
        }
        
        public int getIs_day() {
            return is_day;
        }
        
        public void setIs_day(int is_day) {
            this.is_day = is_day;
        }
        
        public String getTime() {
            return time;
        }
        
        public void setTime(String time) {
            this.time = time;
        }
    }
    
    public static class DailyUnits {
        private String time;
        private String temperature_2m_max;
        private String temperature_2m_min;
        
        public DailyUnits() {}
        
        public String getTime() {
            return time;
        }
        
        public void setTime(String time) {
            this.time = time;
        }
        
        public String getTemperature_2m_max() {
            return temperature_2m_max;
        }
        
        public void setTemperature_2m_max(String temperature_2m_max) {
            this.temperature_2m_max = temperature_2m_max;
        }
        
        public String getTemperature_2m_min() {
            return temperature_2m_min;
        }
        
        public void setTemperature_2m_min(String temperature_2m_min) {
            this.temperature_2m_min = temperature_2m_min;
        }
    }
    
    public static class Daily {
        private List<String> time;
        private List<Double> temperature_2m_max;
        private List<Double> temperature_2m_min;
        
        public Daily() {}
        
        public List<String> getTime() {
            return time;
        }
        
        public void setTime(List<String> time) {
            this.time = time;
        }
        
        public List<Double> getTemperature_2m_max() {
            return temperature_2m_max;
        }
        
        public void setTemperature_2m_max(List<Double> temperature_2m_max) {
            this.temperature_2m_max = temperature_2m_max;
        }
        
        public List<Double> getTemperature_2m_min() {
            return temperature_2m_min;
        }
        
        public void setTemperature_2m_min(List<Double> temperature_2m_min) {
            this.temperature_2m_min = temperature_2m_min;
        }
    }
}
