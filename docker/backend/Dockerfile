# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the pre-built JAR file into the container
COPY ./target/rental-manager-0.0.1-SNAPSHOT.jar rental-manager.jar

# Expose the backend port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "rental-manager.jar"]