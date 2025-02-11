package com.agrowise.WeatherForecast.service;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class WeatherForecastService {

    private static final Map<String, Integer> CITY_ID_MAP = Map.of(
            "Kalamata", 261604,
            "Pthiotis", 262187,
            "Heraklion", 8133920,
            "Chania", 260114,
            "Sparti", 253394
    );

    // API key moved to application.properties
    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherForecastService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to fetch weather data for a city
    public List<WeatherForecast> getWeatherData(String city) {
        int cityId = getCityId(city);
        String url = buildUrl(cityId);

        String response = restTemplate.getForObject(url, String.class);
        return parseWeatherData(response);
    }

    // Method to validate if the city exists
    public boolean isCityValid(String city) {
        return CITY_ID_MAP.containsKey(city);
    }

    // Simplified this method since it's just returning the keys of CITY_ID_MAP
    public List<String> getCities() {
        return List.copyOf(CITY_ID_MAP.keySet());
    }

    // Extract city id lookup to a method
    private int getCityId(String city) {
        return CITY_ID_MAP.getOrDefault(city, -1);
    }

    // Build the API URL dynamically using UriComponentsBuilder
    private String buildUrl(int cityId) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("id", cityId)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")  // Celsius
                .queryParam("cnt", "5")  // Get 5 days forecast
                .toUriString();
    }

    // Parse the weather data from the response
    private List<WeatherForecast> parseWeatherData(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray forecastList = jsonResponse.getJSONArray("list");

        List<WeatherForecast> forecasts = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            JSONObject dayForecast = forecastList.getJSONObject(i);
            double dayTemp = dayForecast.getJSONObject("main").getDouble("temp");
            int humidity = dayForecast.getJSONObject("main").getInt("humidity");
            JSONArray weatherArray = dayForecast.getJSONArray("weather");
            String description = weatherArray.getJSONObject(0).getString("description");
            boolean isRaining = description.contains("rain");

            forecasts.add(new WeatherForecast(dayTemp, humidity, description, isRaining));
        }

        return forecasts;
    }
}
