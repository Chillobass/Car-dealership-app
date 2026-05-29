plugins {
//    id("java")
    id("org.springframework.boot")
}

group = "ru.burunsuzian"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
repositories {
    mavenCentral()
}

dependencies {
    implementation (project(":common"))

    implementation ("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springframework.boot:spring-boot-starter-amqp")

    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")
    implementation ("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.liquibase:liquibase-core")

    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")

    testImplementation("org.testcontainers:postgresql:1.19.7")
    testImplementation("io.grpc:grpc-inprocess:1.65.1")
    testImplementation("org.testcontainers:rabbitmq:1.19.7")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.test {
    useJUnitPlatform()
}