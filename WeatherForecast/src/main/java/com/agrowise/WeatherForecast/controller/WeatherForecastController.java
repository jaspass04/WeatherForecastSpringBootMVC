package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import com.agrowise.WeatherForecast.service.WeatherForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles weather forecast-related requests.
 * It delegates business logic to the WeatherForecastService.
 */
@Controller
@RequestMapping("/agro")
public class WeatherForecastController {

    private final WeatherForecastService weatherService;

    @Autowired
    public WeatherForecastController(WeatherForecastService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * Displays the weather page with a dropdown of available cities.
     *
     * @param model the model used to pass data to the view.
     * @return the name of the Thymeleaf template (weatherPage).
     */
    @GetMapping("/weather")
    public String showWeatherPage(Model model) {
        List<String> cities = weatherService.getCities();  // Fetch cities from the service
        model.addAttribute("cities", cities);
        model.addAttribute("location", null);  // No initial location selected
        return "weatherPage";
    }

    /**
     * Processes the form submission by validating the selected city and fetching weather data.
     *
     * @param location the city selected by the user.
     * @param model    the model used to pass data to the view.
     * @return the name of the Thymeleaf template (weatherPage).
     */
    @PostMapping("/weather")
    public String getWeather(@RequestParam("location") String location, Model model) {
        List<String> cities = weatherService.getCities(); // Fetch cities from the service
        model.addAttribute("cities", cities);

        // Save the user's selection to pre-select it in the dropdown
        model.addAttribute("selectedCity", location);

        if (weatherService.isCityValid(location)) {  // Validate the selected city
            List<WeatherForecast> forecastList = weatherService.getWeatherData(location);
            model.addAttribute("forecastList", forecastList);
        } else {
            model.addAttribute("error", "City not found");
        }

        model.addAttribute("location", location);
        return "weatherPage";
    }
}
