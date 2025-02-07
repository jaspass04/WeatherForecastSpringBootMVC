<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="error.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="agrowise.*"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%
    User user = (User) session.getAttribute("userObj2024");
    if (user == null) {
        request.setAttribute("message", "You are not authorized to access this resource. Please login.");
        request.getRequestDispatcher("login.jsp").forward(request, response);
        return;
    }

    String selectedField = request.getParameter("fieldsSelect");
    String location = request.getParameter("location");

    WeatherForecastService weatherService = new WeatherForecastService();
    WeatherForecast forecastDAO;
    List<WeatherForecastService.WeatherForecast> forecastList = null;
    if (location != null && !location.isEmpty()) {
        try {
            forecastList = WeatherForecastService.getWeatherForecast(location);
        } catch (Exception e) {
            request.setAttribute("message", "Error fetching weather forecast: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<%@ include file="header.jsp" %>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Agro - Weather Forecast</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-theme.min.css">
    <style>
        body {
            color: #333;
            background-image: url('<%= request.getContextPath() %>/images/backImg.jpeg');
            background-size: cover;
            background-repeat: no-repeat;
            background-position: center;
            background-attachment: fixed;
            margin: 0;
            padding: 0;
            padding-top: 20px;
            font-size: 1rem;
            overflow: auto;
        }

        .container.theme-showcase {
            margin-top: 1rem; /* Scalable spacing */
        }

        label {
            font-size: 1.25rem;
            font-weight: bold;
            color: #333 !important;
        }

        select.form-control {
            width: 100%; /* Full width for responsiveness */
            padding: 0.5rem;
            border-radius: 0.3rem;
            border: 1px solid #ccc;
            text-align: left;
        }

        select.form-control:hover {
            border-color: #007bff;
            box-shadow: 0 2px 6px rgba(0, 123, 255, 0.2);
        }

        .weather-forecast {
            margin-top: 1.5rem;
            padding: 1rem;
            background-color: #f9f9f9;
            border-left: 5px solid #4CAF50;
            border-radius: 0.3rem;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .weather-forecast p {
            margin: 0.5rem 0;
            font-size: 1rem;
        }

        @media (max-width: 768px) {
            .weather-forecast {
                font-size: 0.9rem;
                padding: 0.8rem;
            }
            select.form-control {
                font-size: 0.9rem;
            }
        }

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Agro-Wise</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<%= request.getContextPath() %>/agro/index.jsp">Home</a></li>
                <li><a href="<%= request.getContextPath() %>/agro/about.jsp">About</a></li>
                <% if (user != null) { %>
                <li><a href="<%= request.getContextPath() %>/agro/fields.jsp">Fields</a></li>
                <% } %>
                <% if (user != null) { %>
                <li class="active"><a href="<%= request.getContextPath() %>/agro/weather.jsp">Weather Forecast</a></li>
                <% } %>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if (user == null) { %>
                <li><a href="<%= request.getContextPath() %>/agro/register.jsp">Register</a></li>
                <li><a href="<%= request.getContextPath() %>/agro/login.jsp">Sign in</a></li>
                <% } else { %>
                <li>
                    <p class="navbar-text">Signed in as <%= user.getUsername() %></p>
                </li>
                <li>
                    <a href="<%= request.getContextPath() %>/agro/logout.jsp"><span class="glyphicon glyphicon-log-out"></span> Sign out</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>


<div class="container theme-showcase" role="main">
    <div class="page-header">
        <h1>Enter a location for the weather forecast 🌤️</h1>
    </div>
    <form method="post" action="">
        <div class="form-group">
            <label for="location">Enter Location:</label>
            <input type="text" id="location" name="location" class="form-control" value="<%= location != null ? location : "" %>" placeholder="example values: Heraklion, Chania, Kalamata, Sparti, Fthiotida">
        </div>
        <button type="submit" class="btn btn-primary">Get Forecast</button>
    </form>
    <% if (forecastList != null && !forecastList.isEmpty()) {
        LocalDate forecastDate = LocalDate.now().minusDays(1); // Start with today
    %>
    <div class="weather-forecast" style="max-height: 400px; overflow-y: auto;">
        <h3>Weather forecast for <code><%= location %></code>:</h3>
        <%
            for (WeatherForecastService.WeatherForecast forecast : forecastList) {
                forecastDate = forecastDate.plusDays(1); // Increment the date for each forecast
        %>
        <p><strong>Date:</strong> <%= forecastDate %></p>
        <p><strong>Temperature:</strong> <%= String.format("%.1f", forecast.getTemperature()) %>°C</p>
        <p><strong>Humidity:</strong> <%= forecast.getHumidity() %>%</p>
        <p><strong>Conditions:</strong> <%= forecast.getDescription() %></p>
        <hr>
        <% } %>
    </div>
    <% } else { %>
    <p>No weather data available for the specified location.</p>
    <% } %>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
