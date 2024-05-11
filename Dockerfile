# Compile and package the application
FROM maven:3.9.6-sapmachine-21 AS build
COPY . .
RUN mvn clean package -DskipTests

# Run the application
FROM openjdk:21-jdk-slim
COPY --from=build /target/RedRolePlay.jar RedRolePlay.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "RedRolePlay.jar"]
