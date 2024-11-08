FROM maven:3.8.4-openjdk-23 AS build

WORKDIR /app

COPY pom.xml

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=build /app/target/forever-monolith.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/forever-monolith.jar"]