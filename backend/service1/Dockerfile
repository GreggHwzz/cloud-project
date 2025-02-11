FROM openjdk:17
WORKDIR /app
COPY target/service1-0.0.1-SNAPSHOT.jar service1.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "service1.jar" ]