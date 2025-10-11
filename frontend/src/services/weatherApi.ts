import axios from 'axios';
import { WeatherResponse } from '../types/weather';

const API_BASE_URL = 'http://localhost:8080/api';

const weatherApi = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

export const getWeatherForecast = async (address: string): Promise<WeatherResponse> => {
  try {
    const response = await weatherApi.get('/weather/forecast', {
      params: { address }
    });
    return response.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      throw new Error(error.response?.data?.error || 'Failed to fetch weather data');
    }
    throw new Error('An unexpected error occurred');
  }
};

export const checkHealth = async (): Promise<string> => {
  try {
    const response = await weatherApi.get('/weather/health');
    return response.data;
  } catch (error) {
    throw new Error('API is not available');
  }
};
