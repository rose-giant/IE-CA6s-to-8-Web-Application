FROM openjdk:19-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the project's build output (the JAR file) to the working directory in the container
COPY target/mizdooni-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
## Use an official OpenJDK runtime as a parent image
#FROM openjdk:19-jdk-alpine
#
## Set the working directory in the container
#WORKDIR /app
#
## Copy the wait-for-it script to the container
#COPY wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh
#
## Copy the project's build output (the JAR file) to the working directory in the container
#COPY target/mizdooni-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the port your Spring Boot app runs on
#EXPOSE 8080
#
## Run the wait-for-it script to wait for the database and then start the application
#ENTRYPOINT ["./wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
