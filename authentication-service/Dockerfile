# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Run
FROM openjdk:8u302-jre-slim-buster
WORKDIR /app
COPY --from=build /app/target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
