# Weather Forecast API

A Spring Boot REST API that provides weather forecast information based on address input with intelligent caching.

## Features

- Accept address input and retrieve weather forecast data
- Current temperature display (required)
- High/low temperature display (bonus feature)
- 30-minute caching for improved performance
- Cache indicator in API response
- RESTful API design with GET endpoint and query parameters
- **Interactive API Documentation** with Swagger UI
- **OpenAPI 3.0 Specification** for API contracts
- **Try-it-out functionality** for testing endpoints directly
- **Comprehensive examples** and schema documentation

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Maven
- Caffeine Cache
- Open-Meteo Weather API
- **SpringDoc OpenAPI 3** (Swagger UI)
- **OpenAPI 3.0 Specification**

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080`

## API Documentation

### Swagger UI
The API includes comprehensive Swagger/OpenAPI documentation that can be accessed at:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api-docs`

**Features:**
- **Interactive Documentation** - Complete API reference with examples
- **Try-it-out Functionality** - Test endpoints directly from the browser
- **Request/Response Examples** - Realistic sample data for all endpoints
- **Schema Definitions** - Complete data model documentation
- **Error Documentation** - All possible error responses with examples
- **Parameter Descriptions** - Detailed parameter documentation with examples

**Usage:**
1. Start the application: `mvn spring-boot:run`
2. Navigate to `http://localhost:8080/swagger-ui.html`
3. Expand any endpoint to see full documentation
4. Click "Try it out" to test the endpoint
5. Fill in the request parameters and click "Execute"
6. View the response with status code and response body

## API Endpoints

### Health Check
- **GET** `/api/weather/health`
- Returns API status

### Weather Forecast
- **GET** `/api/weather/forecast?address=123 Main St, New York, NY 10001`

### Response Format
```json
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
```

## Testing

The API can be tested using two comprehensive methods:

### Option 1: Swagger UI (Browser-based)
1. Start the application: `mvn spring-boot:run`
2. Open your browser and navigate to: `http://localhost:8080/swagger-ui.html`
3. Use the "Try it out" button on any endpoint to test the API
4. View request/response examples and schemas directly in the UI

### Option 2: Postman Collection (Desktop App)
The project includes a comprehensive Postman collection with 20+ test cases covering:

**Import Instructions:**
1. Open Postman
2. Click "Import" and select `postman/Weather-API-Collection.json`
3. The collection will be imported with all test cases ready to use

**Test Coverage:**
- **Health Check** - API status verification
- **Multiple States** - 12 different US states with major cities
- **GET Endpoint Testing** - Query parameter-based requests
- **Cache Testing** - Verify 30-minute caching behavior
- **Error Handling** - Empty addresses, missing parameters
- **Geographic Coverage** - New York, California, Texas, Florida, Washington, Colorado, Arizona, Nevada, Oregon, Massachusetts, Georgia, Illinois

**Quick Start:**
1. Start the application: `mvn spring-boot:run`
2. Open the imported Postman collection
3. Click any request to test immediately
4. All requests are pre-configured with proper headers and realistic test data

**Which Testing Method to Choose?**
- **Swagger UI**: Best for quick testing, documentation review, and browser-based testing
- **Postman**: Best for comprehensive testing, multiple state coverage, and automated testing scenarios

### Testing Cache Behavior
1. Make a request to any address
2. Make the same request again within 30 minutes
3. The second response will have `"fromCache": true`

## Configuration

The application uses the following configuration in `application.yml`:
- Server port: 8080
- Cache TTL: 30 minutes
- Weather API: Open-Meteo (https://api.open-meteo.com/v1/forecast)

## Project Structure

```
src/
├── main/
│   ├── java/com/forcasty/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── dto/           # Data transfer objects
│   │   └── WeatherApiApplication.java
│   └── resources/
│       └── application.yml # Configuration
postman/
└── Weather-API-Collection.json # Postman collection
```

## Notes

- The geocoding service uses a basic implementation
- In production, integrate with a proper geocoding service
- Weather data is fetched from Open-Meteo API
- Cache automatically expires after 30 minutes
