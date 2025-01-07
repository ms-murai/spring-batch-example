FROM maven:3.8.5-openjdk-17 as build

WORKDIR /app
COPY ./demo .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--debug"]