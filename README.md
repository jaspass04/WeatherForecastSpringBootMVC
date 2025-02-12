# WeatherForecastSpringBootMVC
Transitioning a JSP project use case to an SpringBoot MVC framework. 
## A quick overview:
  Inspired from an agriculture JSP web application's Use Case - which fetches data and presents them in a user friendly display, for the 5 biggest olive producer Greek provinces (Kalamata, Heraklion, Chania, Sparti, Pthiotis) - to create an MVC framework which is using the OpenWeather API and experiments with Docker Containerization!
## Architecture Description:
  This repo takes advantage of Spring Boot to create an MVC architectural design. Let's look its components and more specifically:
  - **WeatherForecastController:**
  A Controller Class which is located in controller package. It handles HTTP requests (GET for getting the app access, via https://localhost:8080/agro/weather endpoint and POST for posting data to the frontend display) while delegates business logic.
  - **WeatherForecastService:**
  A Service class, located in service package. It implements business logic such as API calls, JSON parsing and formatting of Five Provinces.
  - **WeatherForecast:**
  A model class, located in model package with all its setters and getters. It represents the Forecast data entity that is having all the necessary data to be shown in the View (temperature, humidity, description, and if it is raining)
  - **weatherPage.html**
  This is a thymeleaf frontend, located in /resources/templates directory, representing the view layer of the architecture. It displays all the data of a province chosen in a dropdown menu
  - **AppConfig:**
  A configuration class for defining beans.

## Getting Started: a set up guide:
### Prerequisites
- **Java 21**: Ensure you have Java 21 installed. You can verify by running "java -version"
- **Maven**: Install Maven for building the project. Verify by running "mvn -version"
- **Docker**: Install Docker on your system if you plan to run the containerized version.
- **Git**: Required for version control and to clone the repository.

### Lets Build the app!
1. **Clone the Repo:**
   Clone the repository from github to your local machine with command `git clone git@github.com:jaspass04/WeatherForecastSpringBootMVC.git`
   and then get to path of WeatherForecast.
2. **Ensure OpenWeatherAPI key is set:**
   You can establish connection to the weather API with three ways (if not using docker):
   1. by generating your own API key in OpenWeatherAPI official website
   2. by setting either yours or the API key i send you (if you are an authorized user) to application.properties files, where `${OPEN-WEATHER_API_KEY}` placeholder is found
   3. by setting either yours or the API key i sent you (if you are an authorized user) to your environment variables with Environment Variable named OPEN-WEATHER_API_KEY.
3. **Build with Maven:**
   Use maven to build the project and create the executable JAR (which will be generated in a /target folder, useful for our containerization app as well)
4. **Run Locally (Without Docker):**
   Start the app using Maven! Type `mvn spring-boot:run` to your command line or run the JAR file through `java -jar target/WeatherForecast-0.0.1-SNAPSHOT.jar` command. 
5. **Access the Web Application:**
   Open your web browser and navigate to https://localhost:8080/agro/weather
6. **Running with Docker:**
   - **Build the Docker Image:** (the docker image gets built from the dockerfile)
     Ensure the JAR is built (see step 3) and build it by writing `docker build -t weatherforecast-app .`
   - **Run the Docker Container:**
     Run the container which maps port 8080 on host to container and passes the required API key as an environment variable. The command you should send is
      `docker run -p 8080:8080 -e OPEN-WEATHER_API_KEY=valid_api_key weatherforecast-app`
     If your container runs properly, you could proceed to follow the Step 5 and let the magic begin!
     
## Use Case Walkthrough
- Suppose you have completed the set-up correctly, you can now try the application. By navigating to **https://localhost:8080/agro/weather** endpoint, you can:
1. **Select a province:**
Choose a province from the Dropdown Menu.
2. **Submit the form to see its Weather Data:**
Click the "Get Weather" button to fetch the weather forecast for the next 5 days of the selected province.
3. **View the forecast:**
Get informed about the forecast which will be displaying the weather details (temperature, humidity, description and rain status) for the selected province.

## Additional Notes
- The project has been refactored multiple times to ensure it follows best practices for MVC, error handling, and clean code. 
- All sensitive configuration values (e.g., API keys) are provided via environment variables. An Authorized Member can get the existing key via communication with the author, but anyone can accessing by just getting a OpenWeather API key.

## Issues and Limitations

- **API Dependency and Reliability:**
  - The application relies on the OpenWeather API to fetch weather data. If the API is unavailable or if network issues occur, the application may not display forecast information.

- **Limited Forecast Data:**
  - The current implementation only retrieves and displays a fixed number (5) of forecast entries. Depending on the API response, these entries might represent forecasts for the same day (e.g., multiple time slots) rather than distinct days.
  - There is no aggregation logic to group hourly forecasts into daily summaries, which might be needed for a more comprehensive multi-day forecast view.

- **Data Store Considerations:**
  - Although a data store is a requirement, this nature of the given use case of the original application makes unecessary any data store usage so this version of the app is not using any persistent database at all. This is because no specific data are required to be maintained after the restart of the app.

- **Error Handling:**
  - Basic error handling is in place (e.g., displaying "Province not found" for an invalid selection). However, more robust error management (such as handling timeouts, malformed responses, or unresponsive endpoints) may be needed in a prod environment.

- **Containerization and Environment Variables:**
  - The application is containerized with Docker and relies on environment variables (e.g., the API key). If these variables are not properly configured at runtime, the application will fail to resolve critical configuration properties.
  - While the Dockerfile exposes port 8080, mapping this port correctly on different host environments may require additional configuration, such as deleting PIDs runnning before running the current app.

- **Documentation and Future Enhancements:**
  - This version of the project includes basic documentation. However, comprehensive user guides, detailed API documentation, and more extensive inline comments may be needed for future maintenance or for users who require deeper insights into the implementation.
