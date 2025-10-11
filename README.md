# Weather Forecast API

A simple weather API built with Spring Boot that takes an address and returns current weather data. Includes caching to avoid hitting external APIs too frequently.

## What it does

- Takes an address and returns current weather data
- Shows current temperature (required)
- Also shows high/low temperatures (bonus)
- Caches results for 30 minutes to avoid repeated API calls
- Tells you if the data came from cache or was fresh
- Has a simple GET endpoint that's easy to use
- Includes Swagger UI for testing the API
- Logs everything so you can see what's happening

## Built with

- Java 17
- Spring Boot 3.2.0
- Maven for building
- Caffeine for caching
- Open-Meteo API for weather data
- SLF4J + Logback for logging
- Swagger UI for API docs

## Quick Start

You'll need Java 17+ and Maven installed.

### Backend Setup

1. Start the Spring Boot API:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

### Frontend Setup

1. Install dependencies (first time only):
```bash
cd frontend
npm install
```

2. Start the React development server:
```bash
npm run dev
```

The frontend will be available at `http://localhost:3000`

### Running Both

You'll need two terminals:
- Terminal 1: Run the backend with `mvn spring-boot:run`
- Terminal 2: Run the frontend with `cd frontend && npm run dev`

Then open `http://localhost:3000` in your browser.

## API Docs

Once the backend is running, you can check out the API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Raw API docs: `http://localhost:8080/api-docs`

The Swagger UI lets you test the API directly from your browser - just click "Try it out" on any endpoint.

## API Endpoints

**Health check:**
- `GET /api/weather/health` - Just tells you the API is running

**Get weather:**
- `GET /api/weather/forecast?address=123 Main St, New York, NY 10001`

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

## Frontend

There's a simple React app in the `frontend/` folder that gives you a nice UI to test the API.

**Features:**
- Clean, simple interface
- TypeScript for better code quality
- Shows if data came from cache
- Handles errors nicely
- Loading spinners while fetching data

## Testing the API

You can test the API in a few different ways:

**1. Use the React frontend (easiest):**
- Run the start script or start both manually
- Go to `http://localhost:3000`
- Enter an address and click "Get Weather"

**2. Use Swagger UI:**
- Go to `http://localhost:8080/swagger-ui.html`
- Click "Try it out" on the forecast endpoint
- Enter an address and hit Execute

**3. Use Postman:**
- Import the collection from `postman/Weather-API-Collection.json`
- It has requests for different states and test cases
- Good for testing multiple scenarios at once

**4. Use curl:**
```bash
curl "http://localhost:8080/api/weather/forecast?address=123 Main St, New York, NY 10001"
```

### Testing the Cache

To see caching in action:
1. Make a request to any address
2. Make the same request again within 30 minutes
3. The second response will have `"fromCache": true`

## Configuration

The app is configured in `application.yml`:
- Runs on port 8080
- Cache expires after 30 minutes
- Uses Open-Meteo API for weather data
- Logs to console and file

## Project Structure

```
├── src/                          # Backend (Spring Boot)
│   ├── main/
│   │   ├── java/com/forcasty/
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── service/        # Business logic
│   │   │   ├── dto/           # Data transfer objects
│   │   │   └── WeatherApiApplication.java
│   │   └── resources/
│   │       └── application.yml # Configuration
│   └── test/                   # Backend tests
├── frontend/                    # Frontend (React TypeScript)
│   ├── src/
│   │   ├── components/         # React components
│   │   ├── services/          # API service layer
│   │   ├── types/             # TypeScript interfaces
│   │   └── main.tsx           # App entry point
│   ├── package.json           # Frontend dependencies
│   └── README.md              # Frontend documentation
├── postman/
│   └── Weather-API-Collection.json # Postman collection
└── README.md                   # This file
```

## Notes

- The geocoding service is pretty basic (just extracts zip codes from addresses)
- In a real app, you'd want to use a proper geocoding service like Google Maps
- Weather data comes from Open-Meteo API (it's free and doesn't need an API key)
- Cache automatically expires after 30 minutes
