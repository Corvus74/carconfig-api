plugins {
    java
    id("org.springframework.boot") version "4.0.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "7.2.2.6593"
}

group = "com.carconfig"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
    // Define a configuration for the Mockito agent that is isolated from other plugins
    val mockitoAgent by creating {
        isCanBeResolved = true
        isCanBeConsumed = false
        isTransitive = false
    }
}

repositories {
    mavenCentral()
}

// Define versions
val mockitoVersion = "5.19.0"
val mapstructVersion = "1.6.3"
val swaggerOpenapiVersion = "2.8.13"
val apacheCommons = "3.18.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.apache.commons:commons-lang3:$apacheCommons")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerOpenapiVersion")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Add the mockito-inline dependency to our isolated agent configuration
    "mockitoAgent"("org.mockito:mockito-core:$mockitoVersion")
}

sonarqube {
    properties {
        property("sonar.projectKey", "carconfig-api")
        property("sonar.organization", "corvus74") // <-- CHANGE THIS
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.projectKey", "Corvus74_carconfig-api")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory}/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // Configure the JVM arguments for the test task to use the Mockito agent
    jvmArgs("-javaagent:${configurations.getByName("mockitoAgent").singleFile}")
    // This tells Spring Boot to load application-test.yml for tests.
    systemProperties.put("spring.config.name", "application-test")
}

// Ensure JaCoCo XML report is generated for SonarQube
tasks.withType<org.gradle.api.tasks.testing.Test> {
    finalizedBy(tasks.withType<org.gradle.testing.jacoco.tasks.JacocoReport>()) // report is always generated after tests run
}

tasks.withType<org.gradle.testing.jacoco.tasks.JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
