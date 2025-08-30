# Multi-stage Dockerfile for Spring Boot (Java 24)
# Build stage
FROM amazoncorretto:24-jdk AS build
WORKDIR /workspace

# Pre-copy build files for better caching
COPY gradlew .
COPY gradle gradle
COPY settings.gradle.kts .
COPY build.gradle.kts .

# Ensure gradlew is executable
RUN chmod +x ./gradlew

# Download dependencies (will cache unless build files change)
RUN ./gradlew --no-daemon dependencies > /dev/null 2>&1 || true

# Copy the source and build the boot jar (skip tests; tests run in CI test stage)
COPY src src
RUN ./gradlew --no-daemon clean bootJar -x test

# Runtime stage
FROM eclipse-temurin:24-jre
WORKDIR /app

# Copy the jar built in the previous stage
# Find the single boot jar produced by Spring Boot
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
