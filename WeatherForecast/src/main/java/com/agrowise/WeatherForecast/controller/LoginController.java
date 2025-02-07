package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.repository.UsersDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("message", "Invalid username or password");
        }
        return "login"; // Renders login.html
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, RedirectAttributes redirectAttributes) {
        if (("jdoe".equals(username) && "1111".equals(password)) || ("msmith".equals(username) && "2222".equals(password) || ("bjackson".equals(username) && "3333".equals(password)))){
            return "redirect:/forecast"; // Redirects to home page on success
        }
        redirectAttributes.addFlashAttribute("message", "Invalid username or password");
        return "redirect:/login?error=true";
    }
}

