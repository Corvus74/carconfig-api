# Multi-stage Dockerfile for Spring Boot (Java 25)

# --- Dev-Stage: hier läuft das IDE-Backend ---
FROM amazoncorretto:25-jdk AS dev
WORKDIR /workspace
RUN yum install -y findutils git which
CMD ["sleep", "infinity"]

# --- Build-Stage ---
FROM amazoncorretto:25-jdk AS build
WORKDIR /workspace

COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .

RUN chmod +x ./gradlew
RUN yum install -y findutils
RUN ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

COPY src src
RUN ./gradlew --no-daemon clean bootJar -x test

# --- Runtime-Stage ---
FROM amazoncorretto:25

RUN addgroup -S appgroup && adduser -S appuser -G appgroup -u 1001

WORKDIR /app

COPY --chown=appuser:appgroup --from=build /workspace/build/libs/*.jar /app/app.jar

USER appuser

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]