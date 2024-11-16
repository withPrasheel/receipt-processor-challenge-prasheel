FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

# port
EXPOSE 8080

# run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
