# Stage 1: Build
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean install

# Stage 2: Run
FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/metamodel-service-0.0.1-SNAPSHOT.jar metamodel-service.jar
ENTRYPOINT ["java", "-jar", "/metamodel-service.jar"]
