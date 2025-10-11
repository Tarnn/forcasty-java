import React, { useState } from 'react';
import {
  Box,
  TextField,
  Button,
  Card,
  CardContent,
  Typography,
  Alert,
  CircularProgress,
  Chip,
  Grid,
  Divider,
} from '@mui/material';
import { Search, LocationOn, Thermostat, Cloud } from '@mui/icons-material';
import { getWeatherForecast } from '../services/weatherApi';
import { WeatherResponse } from '../types/weather';

const WeatherForecast: React.FC = () => {
  const [address, setAddress] = useState('');
  const [weather, setWeather] = useState<WeatherResponse | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!address.trim()) {
      setError('Please enter an address');
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const data = await getWeatherForecast(address.trim());
      setWeather(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to fetch weather data');
    } finally {
      setLoading(false);
    }
  };

  const formatTimestamp = (timestamp: number) => {
    return new Date(timestamp).toLocaleString();
  };

  return (
    <Box>
      <Card sx={{ mb: 3 }}>
        <CardContent>
          <form onSubmit={handleSubmit}>
            <Box display="flex" gap={2} alignItems="center">
              <TextField
                fullWidth
                label="Enter Address"
                placeholder="e.g., 123 Main St, New York, NY 10001"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                disabled={loading}
                variant="outlined"
              />
              <Button
                type="submit"
                variant="contained"
                disabled={loading}
                startIcon={loading ? <CircularProgress size={20} /> : <Search />}
                sx={{ minWidth: 120 }}
              >
                {loading ? 'Loading...' : 'Get Weather'}
              </Button>
            </Box>
          </form>
        </CardContent>
      </Card>

      {error && (
        <Alert severity="error" sx={{ mb: 3 }}>
          {error}
        </Alert>
      )}

      {weather && (
        <Card>
          <CardContent>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
              <Typography variant="h5" component="h2" display="flex" alignItems="center" gap={1}>
                <LocationOn color="primary" />
                {weather.address}
              </Typography>
              <Chip
                label={weather.fromCache ? 'From Cache' : 'Live Data'}
                color={weather.fromCache ? 'secondary' : 'primary'}
                variant="outlined"
              />
            </Box>

            <Divider sx={{ mb: 3 }} />

            <Grid container spacing={3}>
              <Grid item xs={12} md={4}>
                <Box textAlign="center">
                  <Typography variant="h2" color="primary" display="flex" alignItems="center" justifyContent="center" gap={1}>
                    <Thermostat />
                    {weather.currentTemperature}
                    <Typography variant="h4" component="span">
                      {weather.temperatureUnit}
                    </Typography>
                  </Typography>
                  <Typography variant="h6" color="text.secondary">
                    Current Temperature
                  </Typography>
                </Box>
              </Grid>

              <Grid item xs={12} md={4}>
                <Box textAlign="center">
                  <Typography variant="h4" color="success.main">
                    {weather.highTemperature}{weather.temperatureUnit}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    High
                  </Typography>
                </Box>
              </Grid>

              <Grid item xs={12} md={4}>
                <Box textAlign="center">
                  <Typography variant="h4" color="info.main">
                    {weather.lowTemperature}{weather.temperatureUnit}
                  </Typography>
                  <Typography variant="body1" color="text.secondary">
                    Low
                  </Typography>
                </Box>
              </Grid>
            </Grid>

            <Box mt={3} textAlign="center">
              <Typography variant="h6" display="flex" alignItems="center" justifyContent="center" gap={1}>
                <Cloud />
                {weather.description}
              </Typography>
            </Box>

            <Box mt={2} textAlign="center">
              <Typography variant="body2" color="text.secondary">
                Zip Code: {weather.zipCode} | Updated: {formatTimestamp(weather.timestamp)}
              </Typography>
            </Box>
          </CardContent>
        </Card>
      )}
    </Box>
  );
};

export default WeatherForecast;
