package com.agrowise.WeatherForecast.service;

import com.agrowise.WeatherForecast.model.User;
import com.agrowise.WeatherForecast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;
    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return An Optional containing the user if found, otherwise empty.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Saves a new user to the database.
     *
     * @param user The user object to save.
     * @return The saved user object.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    /**
     * Validates if the provided username and password match an existing user.
     *
     * @param username The username to check.
     * @param password The password to check.
     * @return True if the username exists and the password matches, otherwise false.
     */
    public boolean validateUserCredentials(String username, String password) {
        Optional<User> user = findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    /**
     * Checks if the given username is already taken.
     *
     * @param username The username to check.
     * @return True if the username is already taken, otherwise false.
     */
    public boolean isUsernameTaken(String username) {
        Optional<User> user = findByUsername(username);
        return user.isPresent();
    }

    /**
     * Registers a new user by saving them to the database.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     */
    public void registerNewUser(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        saveUser(newUser);
    }
}
