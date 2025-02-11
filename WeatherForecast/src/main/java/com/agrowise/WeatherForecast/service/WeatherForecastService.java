package com.agrowise.WeatherForecast.service;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Service layer responsible for fetching and parsing weather data from the external API.
 */
@Service
public class WeatherForecastService {

    private static final Map<String, Integer> CITY_ID_MAP = Map.of(
            "Kalamata", 261604,
            "Pthiotis", 262187,
            "Heraklion", 8133920,
            "Chania", 260114,
            "Sparti", 253394
    );

    @Value("${weather.api.key}")
    private String apiKey;

    @Value("${weather.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public WeatherForecastService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches weather data for the given city name.
     *
     * @param city the name of the city.
     * @return a list of WeatherForecast objects parsed from the API response.
     */
    public List<WeatherForecast> getWeatherData(String city) {
        int cityId = getCityId(city);
        String url = buildUrl(cityId);
        String response = restTemplate.getForObject(url, String.class);
        return parseWeatherData(response);
    }

    /**
     * Checks if the given city is valid (present in the predefined city map).
     *
     * @param city the name of the city.
     * @return true if the city is valid; false otherwise.
     */
    public boolean isCityValid(String city) {
        return CITY_ID_MAP.containsKey(city);
    }

    /**
     * Returns a list of all supported cities.
     *
     * @return an immutable list of city names.
     */
    public List<String> getCities() {
        return List.copyOf(CITY_ID_MAP.keySet());
    }

    /**
     * Retrieves the city ID for the given city name.
     *
     * @param city the name of the city.
     * @return the corresponding city ID, or -1 if not found.
     */
    private int getCityId(String city) {
        return CITY_ID_MAP.getOrDefault(city, -1);
    }

    /**
     * Dynamically builds the API URL using the city ID and query parameters.
     *
     * @param cityId the ID of the city.
     * @return the complete URL for the API call.
     */
    private String buildUrl(int cityId) {
        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("id", cityId)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")  // Celsius
                .queryParam("cnt", "5")           // 5 forecast entries
                .toUriString();
    }

    /**
     * Parses the weather API response into a list of WeatherForecast objects.
     * <p>
     * The method extracts temperature, humidity, weather description, rain status, and converts
     * the UNIX timestamp from the response into a LocalDate.
     * </p>
     *
     * @param response the JSON response from the weather API.
     * @return a list of parsed WeatherForecast objects.
     */
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


            // Create WeatherForecast object
            forecasts.add(new WeatherForecast(dayTemp, humidity, description, isRaining));
        }

        return forecasts;
    }
}
