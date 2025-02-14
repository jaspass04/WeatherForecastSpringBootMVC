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
## And NOW officially offering authentication feature, of login with:
- **LoginController:**
  A login controller for controlling basic registration and login get-post requests. Still delegates business logic
- **LoginService:**
A service, responsible for saving-registering user after validating user credentials, checking if a username is already taken etc. Handles all basic business logic
- **User**
  Basic User Model class, for representing the user entity with his credentials as attributes.
- **UserRepository:**
  Basic repository class for handling jpa logic and storing users in the MYSQL database.
## Getting Started: a set up guide:
### Prerequisites
- **Java 21**: Ensure you have Java 21 installed. You can verify by running "java -version"
- **Maven**: Install Maven for building the project. Verify by running "mvn -version"
- **Docker and Docker-Compose**: Install Docker on your system if you plan to run the containerized version.
- **Git**: Required for version control and to clone the repository.

### Lets Build the app!
1. **Clone the Repo:**
   Clone the repository from github to your local machine with command `git clone git@github.com:jaspass04/WeatherForecastSpringBootMVC.git`
   and then get to path of WeatherForecast.
2. **Ensure Environment Variables are set:**
   You can establish connection to the weather API and mySQL with two ways:
   1. Request the env. variables i used, and add them to your cloned repository
   2. Create your own OPENWEATHER-API key by accessing the official website, also use your Own data source credentials (URL, UserName, Password, jpa hibernate ddl and database platform)
   Create a .env file with
  `touch .env`
    Then, store the environment variables in a .env file (MAKE SURE YOU HAVE PLUGINS FOR RECOGNIZING THE .ENV FILE)
 
4. **Build with Maven:**
   Use maven to build the project and create the executable JAR (which will be generated in a /target folder, useful for our containerization app as well)
5. **Run Locally (Without Docker):**
   Start the app using Maven! Type `mvn spring-boot:run` to your command line or run the JAR file through `java -jar target/WeatherForecast-0.0.1-SNAPSHOT.jar` command. 
6. **Access the Web Application:**
   Open your web browser and navigate to https://localhost:8080/auth/login if you are already registered, or https://localhost:8080/auth/register if you want to register a new account. If you accidentally have been to one or another page, don't worry! you can navigate between those with UI buttons as shown below:
   ![image](https://github.com/user-attachments/assets/885ef029-3898-4cd5-a468-c5d61062749c)

 - If you create credentials, you can login in the login page, and access automatically the https:/localhost:8080/agro/weather endpoint ,which will take you to the application functionality. Feel free to play with weather API calls!
8. **Running with Docker:**
   - To run with docker you have to:
   - **Build the Docker-Compose:** 
     Ensure the JAR is built (see step 3) and build it by writing `docker-compose up build` to run the docker-compose command and connect the Spring App Container with the MySQL Container!
  - If your container runs properly, you could proceed to follow the Step 6 and let the magic begin!
     
## Use Case Walkthrough
- Suppose you have completed the set-up correctly, you can now try the application. By navigating to **https://localhost:8080/auth/register** endpoint, you can:
1. **Register a new account, by signing up with a new username and password. If username is already taken, you should get an error message encouraging you to use another username.**
2. **Login with your new credentials: You will be redirected to the login page (if you are using for a second time the app and already have credentials, just navigate to https://localhost:8080/auth/login and add your existing credentials. If your credentials do not match, then you should get an error message.**
3. **Sign in and access https://localhost:8080/agro/weather endpoint! From now on you can:**
  - **Select a province:**
  Choose a province from the Dropdown Menu.
 -  **Submit the form to see its Weather Data:**
  Click the "Get Weather" button to fetch the weather forecast for the next 5 days of the selected province.
 -  **View the forecast:**
  Get informed about the forecast which will be displaying the weather details (temperature, humidity, description and rain status) for the selected province.

## Additional Notes
- The project has been refactored multiple times to ensure it follows best practices for MVC, error handling, and clean code. 
- All sensitive configuration values (e.g., API keys) are provided via environment variables. An Authorized Member can get the existing key via communication with the author, but anyone can accessing by just getting his/hers/theirs own credentials.

## Issues and Limitations

- **API Dependency and Reliability:**
  - The application relies on the OpenWeather API to fetch weather data. If the API is unavailable or if network issues occur, the application may not display forecast information.

- **Limited Forecast Data:**
  - The current implementation only retrieves and displays a fixed number (5) of forecast entries. Depending on the API response, these entries might represent forecasts for the same day (e.g., multiple time slots) rather than distinct days.
  - There is no aggregation logic to group hourly forecasts into daily summaries, which might be needed for a more comprehensive multi-day forecast view.

- **Data Store Considerations:**
  - Although a data store is a requirement, this nature of the given use case of the original application makes unecessary any data store usage so this version of the app is not using any persistent database at all. This is because no specific data are required to be maintained after the restart of the app. This is why for the requirements of this project a user login logic is implemented, to show that handling mySQL access is a knowledge well exercised and that some form of database is used.

- **Error Handling:**
  - Basic error handling is in place (e.g., displaying "Province not found" for an invalid selection). However, more robust error management (such as handling timeouts, malformed responses, or unresponsive endpoints) may be needed in a prod environment.

- **Containerization and Environment Variables:**
  - The application is containerized with Docker-Compose and relies on environment variables (e.g., the API key). If these variables are not properly configured at runtime, the application will fail to resolve critical configuration properties.
  - While the Docker-Compose exposes port 8080, mapping this port correctly on different host environments may require additional configuration, such as deleting PIDs runnning before running the current app.

- **Documentation and Future Enhancements:**
  - This version of the project includes basic documentation. However, comprehensive user guides, detailed API documentation, and more extensive inline comments may be needed for future maintenance or for users who require deeper insights into the implementation.
