export interface WeatherResponse {
  address: string;
  zipCode: string;
  currentTemperature: number;
  temperatureUnit: string;
  highTemperature: number;
  lowTemperature: number;
  description: string;
  fromCache: boolean;
  timestamp: number;
}

export interface WeatherError {
  error: string;
}
