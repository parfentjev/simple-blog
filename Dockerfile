FROM maven:3.9.11-amazoncorretto-25-alpine AS build
WORKDIR /app
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package

FROM maven:3.9.11-amazoncorretto-25-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
