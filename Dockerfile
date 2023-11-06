FROM openjdk:17-alpine

EXPOSE 8080
WORKDIR /data/www

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
