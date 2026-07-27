FROM maven:3.9.0-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .


RUN mvn dependency:resolve


COPY src/ src/

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app


COPY --from=builder /app/target/*.jar app.jar


EXPOSE 8083


HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
    CMD wget --quiet --tries=1 --spider http://localhost:8083/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]