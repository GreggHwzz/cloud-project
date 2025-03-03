
FROM openjdk:17
WORKDIR /app
COPY target/todo-service-0.0.1-SNAPSHOT.jar todo-service.jar
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "todo-service.jar"]