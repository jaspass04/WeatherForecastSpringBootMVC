package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import com.agrowise.WeatherForecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/agro")
public class WeatherForecastController {

    private final WeatherForecastService weatherService;

    @Autowired
    public WeatherForecastController(WeatherForecastService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String showWeatherPage(Model model) {
        List<String> cities = weatherService.getCities();  // Fetch cities from the service
        model.addAttribute("cities", cities);
        model.addAttribute("location", null);  // No initial location selected
        return "weatherPage";
    }

    @PostMapping("/weather")
    public String getWeather(@RequestParam("location") String location, Model model) {
        List<String> cities = weatherService.getCities(); // Fetch cities again from the service
        model.addAttribute("cities", cities);

        // Add the selected city to the model
        model.addAttribute("selectedCity", location);

        if (weatherService.isCityValid(location)) {  // Validate city
            List<WeatherForecast> forecastList = weatherService.getWeatherData(location);
            model.addAttribute("forecastList", forecastList);
        } else {
            model.addAttribute("error", "City not found");
        }

        model.addAttribute("location", location);
        return "weatherPage";
    }
}
