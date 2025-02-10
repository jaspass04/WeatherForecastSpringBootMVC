package com.agrowise.WeatherForecast.controller;

//import com.agrowise.WeatherForecast.model.WeatherForecast;
import com.agrowise.WeatherForecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/weather")
public class WeatherForecastController {
    private final WeatherForecastService weatherService;

    public WeatherForecastController(WeatherForecastService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/forecast")
    public Mono<ResponseEntity<List<Map<String, Object>>>> getWeatherForecasts() {
        return weatherService.getForecastsForCities()
                .map(ResponseEntity::ok);
    }
}
