# Stage 2: Build
FROM maven:latest AS build
WORKDIR /root
RUN mkdir -p .m2/repository
COPY .m2/repository /root/.m2/repository
WORKDIR /app
COPY . .
# Stage 3: Run
FROM openjdk:24-oracle
VOLUME /tmp
WORKDIR /app
COPY --from=build /app/target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
ENTRYPOINT ["java", "-jar", "user-service.jar"]
