# Стъпка 1: Изграждане на проекта
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Стъпка 2: Стартиране на готовия сайт
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 7860
ENV PORT=7860
ENTRYPOINT ["java", "-jar", "app.jar"]