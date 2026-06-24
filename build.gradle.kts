plugins {
    java
    id("org.springframework.boot") version "4.1.0"
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
}

repositories {
    mavenCentral()
}

// Define versions
val mapstructVersion = "1.6.3"
val swaggerOpenapiVersion = "3.0.3"
val apacheCommons = "3.20.0"
val jjwtVersion = "0.13.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.apache.commons:commons-lang3:$apacheCommons")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$swaggerOpenapiVersion")
    implementation("io.jsonwebtoken:jjwt-api:$jjwtVersion")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:$jjwtVersion")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:$jjwtVersion")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.flywaydb:flyway-database-postgresql")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.springframework.boot:spring-boot-data-jpa-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sonarqube {
    properties {
        property("sonar.organization", "corvus74")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.projectKey", "Corvus74_carconfig-api")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.coverage.jacoco.xmlReportPaths", "${layout.buildDirectory}/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    // This tells Spring Boot to load application-test.yml for tests.
    systemProperties.put("spring.config.name", "application-test")
}

// Ensure JaCoCo XML report is generated for SonarQube
tasks.withType<Test> {
    finalizedBy(tasks.withType<JacocoReport>()) // report is always generated after tests run
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}
