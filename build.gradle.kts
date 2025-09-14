plugins {
    java
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
}

group = "com.carconfig"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
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

tasks.withType<Test> {
    useJUnitPlatform()
    // Configure the JVM arguments for the test task to use the Mockito agent
    jvmArgs("-javaagent:${configurations.getByName("mockitoAgent").singleFile}")
    // This tells Spring Boot to load application-test.yml for tests.
    systemProperties.put("spring.config.name", "application-test")
}
jacoco {
    toolVersion = "0.8.13"
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // Run after tests
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco"))
    }
}
tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.jacocoTestReport) // Run after the report is generated
    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal() // Set minimum line coverage to 80%
            }
        }
    }
}

// Make the standard 'check' task depend on the coverage verification
tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}
