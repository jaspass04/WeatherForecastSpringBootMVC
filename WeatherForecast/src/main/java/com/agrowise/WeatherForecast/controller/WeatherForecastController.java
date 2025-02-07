package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.model.WeatherForecast;
import com.agrowise.WeatherForecast.service.WeatherForecastService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/weather")
public class WeatherForecastController {
    private WeatherForecastService weatherForecastService;
     @GetMapping
    public String showWeatherForm(Model model, HttpSession session) {
         Object user = session.getAttribute("userObj2024");
         if (user == null) {
             model.addAttribute("message", "You are not authorized to access this resource. Please login.");
             return "login";  // send user to login.html, the login page to validate credentials
             //TODO: create login.html page
         }
         //TODO: create Weather.html page
         return "weather"; // sends user to weather.html, the View of our use case
     }
    @PostMapping
    public String getWeatherForecast(@RequestParam("location") String location,
                                     Model model,
                                     HttpSession session) {
        Object user = session.getAttribute("userObj2024");
        if (user == null) {
            model.addAttribute("message", "You are not authorized to access this resource. Please login.");
            // TODO: create login .html
            return "login";
        }
        model.addAttribute("location", location);
        try {
            List<WeatherForecast> forecastList = weatherForecastService.getWeatherForecast(location);
            model.addAttribute("forecasts", forecastList);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "weather";
    }
     }

