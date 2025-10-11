# Weather Forecast UI

A simple React TypeScript frontend for the Weather Forecast API.

## Features

- **Modern UI**: Built with Material-UI (MUI) for a clean, professional look
- **TypeScript**: Full type safety and better development experience
- **Responsive Design**: Works on desktop and mobile devices
- **Real-time Data**: Shows live weather data with cache indicators
- **Error Handling**: User-friendly error messages
- **Loading States**: Visual feedback during API calls

## Technology Stack

- **React 18** - Modern React with hooks
- **TypeScript** - Type-safe JavaScript
- **Material-UI (MUI)** - Google's Material Design components
- **Axios** - HTTP client for API calls
- **Vite** - Fast build tool and dev server

## Getting Started

### Prerequisites

- Node.js 16+ and npm
- Weather API running on `http://localhost:8080`

### Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open your browser and navigate to `http://localhost:3000`

### Building for Production

```bash
npm run build
```

The built files will be in the `dist` directory.

## Usage

1. Enter an address in the input field (e.g., "123 Main St, New York, NY 10001")
2. Click "Get Weather" to fetch the forecast
3. View the current temperature, high/low temperatures, and weather description
4. The cache indicator shows whether data is from cache or live

## API Integration

The frontend connects to the Weather API running on port 8080. Make sure your Spring Boot API is running before using the UI.

### Endpoints Used

- `GET /api/weather/forecast?address={address}` - Get weather forecast
- `GET /api/weather/health` - Health check (for future use)

## Project Structure

```
frontend/
├── src/
│   ├── components/
│   │   └── WeatherForecast.tsx    # Main weather component
│   ├── services/
│   │   └── weatherApi.ts          # API service layer
│   ├── types/
│   │   └── weather.ts             # TypeScript interfaces
│   ├── App.tsx                    # Main app component
│   └── main.tsx                   # App entry point
├── package.json                   # Dependencies and scripts
├── vite.config.ts                 # Vite configuration
└── tsconfig.json                  # TypeScript configuration
```
