package com.agrowise.WeatherForecast.model;

public class WeatherForecast {
    private double temperature;
    private int humidity;
    private String description;
    private boolean isRaining;

    public WeatherForecast(double temperature, int humidity, String description, boolean isRaining) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.isRaining = isRaining;
    }

    // Getters and toString method
    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public boolean isRaining() {
        return isRaining;
    }

    @Override
    public String toString() {
        return String.format("Temp: %.1f°C, Humidity: %d%%, Description: %s, Raining: %b",
                temperature, humidity, description, isRaining);
    }
}
