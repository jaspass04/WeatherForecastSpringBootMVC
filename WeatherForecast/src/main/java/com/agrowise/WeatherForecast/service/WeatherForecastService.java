package com.agrowise.WeatherForecast.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;


public class WeatherForecastService {

    private static final Logger logger = Logger.getLogger(WeatherForecastService.class.getName());


    // Constants for API and city IDs
    private static final int KALAMATA_ID = 261604;
    private static final int PTHIOTIS_ID = 262187;
    private static final int HERAKLION_ID = 8133920;
    private static final int CHANIA_ID = 260114;
    private static final int SPARTI_ID = 253394;
    private static final double KELVIN_BASE_TEMP = 273.15;
    private static final String API_KEY = "53e49e0f97af8623f895ce9dee722188";

    public static List<WeatherForecast> getWeatherForecast(String location) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        int cityId;


        switch (location) {
            case "Kalamata":
                cityId = KALAMATA_ID;
                break;
            case "Pthiotida":
                cityId = PTHIOTIS_ID;
                break;
            case "Heraklion":
                cityId = HERAKLION_ID;
                break;
            case "Chania":
                cityId = CHANIA_ID;
                break;
            case "Sparti":
                cityId = SPARTI_ID;
                break;
            default:
                throw new IllegalArgumentException("Invalid location name. Use Heraklion, Chania, Sparti, Pthiotida, or Kalamata.");
        }

        String apiUrl = String.format(
                "https://api.openweathermap.org/data/2.5/forecast?id=%d&appid=%s",
                cityId, API_KEY);

        try {
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray forecastList = jsonResponse.getJSONArray("list");

                for (int i = 0; i < 5; i++) {
                    JSONObject dayForecast = forecastList.getJSONObject(i);
                    double dayTemp = dayForecast.getJSONObject("main").getDouble("temp") - KELVIN_BASE_TEMP;
                    int humidity = dayForecast.getJSONObject("main").getInt("humidity");
                    JSONArray weatherArray = dayForecast.getJSONArray("weather");
                    String description = weatherArray.getJSONObject(0).getString("description");
                    boolean isRaining = description.contains("rain");

                    forecasts.add(new WeatherForecast(dayTemp, humidity, description, isRaining));
                }
            } else {
                logger.warning("Failed to fetch weather data. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred:", e);
        }

        return forecasts;
    }

    public static void main(String[] args) {
        List<WeatherForecast> forecasts = getWeatherForecast("Kalamata");
        for (WeatherForecast forecast : forecasts) {
            System.out.println(forecast);
        }
    }
}
