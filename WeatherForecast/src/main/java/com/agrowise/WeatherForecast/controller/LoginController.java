package com.agrowise.WeatherForecast.controller;

import com.agrowise.WeatherForecast.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling login and registration functionality.
 */
@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserLoginService userService;

    /**
     * Displays the login page.
     *
     * @return The name of the login page template.
     */
    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    /**
     * Handles the login form submission.
     * Uses UserService to validate the username and password.
     *
     * @param username The username entered in the login form.
     * @param password The password entered in the login form.
     * @param model The model object to add attributes for error handling.
     * @return A redirect to the weather dashboard if credentials are valid, or the login page with an error message.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        boolean isValid = userService.validateUserCredentials(username, password);

        if (isValid) {
            return "redirect:/agro/weather";  // Redirect to weather dashboard
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "loginPage";
        }
    }

    /**
     * Displays the registration page.
     *
     * @return The name of the registration page template.
     */
    @GetMapping("/register")
    public String showRegisterForm() {
        return "registrationPage";
    }

    /**
     * Handles the registration form submission.
     * Uses UserService to check if username is available and save the new user.
     *
     * @param username The username entered in the registration form.
     * @param password The password entered in the registration form.
     * @param model The model object to add attributes for success/error messages.
     * @return The registration page with an error message if the username is already taken, or the login page with a success message.
     */
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        boolean isUsernameTaken = userService.isUsernameTaken(username);

        if (isUsernameTaken) {
            model.addAttribute("error", "Username is already in use");
            return "registrationPage";
        } else {
            userService.registerNewUser(username, password);
            model.addAttribute("success", "Registration successful! You can now login.");
            return "loginPage";  // Redirect to login page
        }
    }
}
