# Build da aplicacao Spring Boot com Java 17 e Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests clean package

# Imagem de execucao menor
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/agrix-*.jar /app/app.jar
EXPOSE 10000
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar /app/app.jar"]
