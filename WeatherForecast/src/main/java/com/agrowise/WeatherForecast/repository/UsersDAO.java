package com.agrowise.WeatherForecast.repository;

import java.util.HashMap;
import java.util.Map;

public class UsersDAO {
    private static final Map<String, String> users = new HashMap<>();

    static {
        users.put("jdoe", "1111");
        users.put("msmith", "2222");
        users.put("bjakson", "3333");
    }

    public static boolean validate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }}