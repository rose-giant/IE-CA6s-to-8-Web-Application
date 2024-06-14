#FROM openjdk:19-jdk-alpine
FROM eclipse-temurin:19
WORKDIR /app
COPY target/mizdooni-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]