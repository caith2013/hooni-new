FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.net.preferIPv4Stack=true", "-jar", "/app/app.jar"]
