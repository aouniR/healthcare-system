# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -Pstage

# Stage 2: Run
FROM openjdk:24-slim
WORKDIR /app
COPY --from=build /app/target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=stage", "-jar", "config-server.jar"]
