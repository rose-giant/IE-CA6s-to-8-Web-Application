FROM openjdk:19-jdk-alpine
ADD target/Mizdooni-1.0-SNAPSHOT.jar mizdooni.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","mizdooni.jar"]