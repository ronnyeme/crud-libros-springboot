# Etapa 1: Compilar con Java 26
FROM eclipse-temurin:26-jdk AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

# Etapa 2: Ejecutar con Java 26
FROM eclipse-temurin:26-jre

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]