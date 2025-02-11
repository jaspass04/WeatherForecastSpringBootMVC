# Use OpenJDK 17 base image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the jar file into the container
COPY target/WeatherForecast-0.0.1-SNAPSHOT.jar /app/WeatherForecast-0.0.1-SNAPSHOT.jar

# Expose the port your application will run on (default is 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "WeatherForecast-0.0.1-SNAPSHOT.jar"]
