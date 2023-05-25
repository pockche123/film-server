FROM openjdk:19
EXPOSE 8080
COPY target/film-app-server.jar film-app-server.jar
ENTRYPOINT ["java", "-jar", "film-app-server.jar"]
