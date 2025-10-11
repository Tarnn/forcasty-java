import React from 'react'
import { Container, Typography, Box } from '@mui/material'
import WeatherForecast from './components/WeatherForecast'

function App() {
  return (
    <Container maxWidth="md" sx={{ py: 4 }}>
      <Box textAlign="center" mb={4}>
        <Typography variant="h3" component="h1" gutterBottom color="primary">
          Weather Forecast
        </Typography>
        <Typography variant="h6" color="text.secondary">
          Get current weather information for any address
        </Typography>
      </Box>
      <WeatherForecast />
    </Container>
  )
}

export default App
