FROM maven:3.8.4-openjdk-21

WORKDIR /app

# Change COPY to reference backend directory from root context
COPY backend/ .

EXPOSE 8080

RUN ls -la

# Build and run the application using Maven directly
CMD ["mvn", "spring-boot:run"]
