#FROM eclipse-temurin:17-jdk-alpine
#EXPOSE 8080
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/RedRolePlay.jar RedRolePlay.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","RedRolePlay.jar"]

