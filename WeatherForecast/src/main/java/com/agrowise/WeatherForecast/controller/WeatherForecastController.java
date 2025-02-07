package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.service.WeatherForecastService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/weather")
public class WeatherForecastController {
    private WeatherForecastService weatherForecastService;
     @GetMapping
    public String weatherForecast(Model model, HttpServletRequest request) {
         Object user = session.getAttribute("userObj2024");

     }
}
