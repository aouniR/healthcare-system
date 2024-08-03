# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Run
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]
