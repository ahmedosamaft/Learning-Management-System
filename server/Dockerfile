FROM openjdk:23-jdk-slim-bullseye
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 9090

ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=db-docker"]