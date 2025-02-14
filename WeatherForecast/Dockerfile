FROM openjdk:21-slim

WORKDIR /app

COPY target/WeatherForecast-0.0.1-SNAPSHOT.jar /app/WeatherForecast-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "WeatherForecast-0.0.1-SNAPSHOT.jar"]
