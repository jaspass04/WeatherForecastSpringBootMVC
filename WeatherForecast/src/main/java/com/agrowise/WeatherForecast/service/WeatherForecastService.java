package com.agrowise.WeatherForecast.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.agrowise.WeatherForecast.config.WebClientConfig;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WeatherForecastService {

    private static final int KALAMATA_ID = 261604;
    private static final int PTHIOTIS_ID = 262187;
    private static final int HERAKLION_ID = 8133920;
    private static final int CHANIA_ID = 260114;
    private static final int SPARTI_ID = 253394;
    private static final double KELVIN_BASE_TEMP = 273.15;

    @Value("${openweather.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public WeatherForecastService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openweathermap.org/data/2.5").build();
    }

    public Mono<Map> getForecast(int cityId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/forecast")
                        .queryParam("id", cityId)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::extractRelevantData);
    }

    public Mono<List<Map<String, Object>>> getForecastsForCities() {
        List<Integer> cityIds = List.of(KALAMATA_ID, PTHIOTIS_ID, HERAKLION_ID, CHANIA_ID, SPARTI_ID);

        return Mono.zip(cityIds.stream().map(this::getForecast).collect(Collectors.toList()),
                results -> List.of(results).stream()
                        .map(o -> (Map<String, Object>) o)
                        .collect(Collectors.toList()));
    }
    private Map<String, Object> extractRelevantData(Map<String, Object> forecastData) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) forecastData.get("list");
        if (list == null || list.isEmpty()) {
            return Map.of();
        }

        Map<String, Object> firstEntry = list.get(0);
        Map<String, Object> main = (Map<String, Object>) firstEntry.get("main");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) firstEntry.get("weather");

        double temperature = main != null ? (double) main.get("temp") : 0;
        int humidity = main != null ? (int) main.get("humidity") : 0;
        String description = !weatherList.isEmpty() ? (String) weatherList.get(0).get("description") : "";
        boolean isRaining = description.toLowerCase().contains("rain");

        return Map.of(
                "temperature", temperature,
                "humidity", humidity,
                "description", description,
                "isRaining", isRaining
        );

}}
