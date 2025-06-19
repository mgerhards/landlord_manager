# Use an official Java runtime as a base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file (ensure your backend is built before this step)
COPY target/rental-manager.jar rental-manager.jar

# Expose the backend port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "rental-manager.jar"]