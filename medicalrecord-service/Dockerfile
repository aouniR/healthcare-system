# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Run
FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/medical-record-service-0.0.1-SNAPSHOT.jar medical-record-service.jar
ENTRYPOINT ["java", "-jar", "/medical-record-service.jar"]
