# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests  -Pstage

# Stage 2: Run
FROM openjdk:24-slim
WORKDIR /app
COPY --from=build /app/target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=stage", "-jar", "notification-service.jar"]

