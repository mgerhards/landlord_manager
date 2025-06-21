FROM maven:3.9.6-eclipse-temurin-21

WORKDIR /app

# Copy pom.xml and pre-fetch dependencies
COPY ../../backend/pom.xml ./
RUN mvn dependency:go-offline

# Copy source (mounted later anyway)
COPY ../../backend /app

EXPOSE 8080 5005

CMD ["bash"]
