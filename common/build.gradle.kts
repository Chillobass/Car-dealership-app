import com.google.protobuf.gradle.id

plugins {
    id("java-library")
    id("org.springframework.boot")
    id("com.google.protobuf")
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}

group = "ru.burunsuzian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.7")
    }
}

val grpcVersion: String by project
val protobufVersion: String by project
val grpcSpringStarterVersion: String by project

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api ("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    api("org.springframework.boot:spring-boot-starter-amqp")

    api("org.springframework.boot:spring-boot-starter-actuator")
    api("io.micrometer:micrometer-tracing-bridge-brave")

    compileOnly ("org.projectlombok:lombok:1.18.36")
    annotationProcessor ("org.projectlombok:lombok:1.18.36")
    implementation ("org.mapstruct:mapstruct:1.6.3")
    annotationProcessor ("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    api("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
    api("org.keycloak:keycloak-admin-client:24.0.0")

    api("io.grpc:grpc-protobuf:$grpcVersion")
    api("io.grpc:grpc-stub:$grpcVersion")
    api("io.grpc:grpc-netty-shaded:$grpcVersion")
    api("com.google.protobuf:protobuf-java:$protobufVersion")

    compileOnly("org.apache.tomcat:annotations-api:6.0.53")
    api("net.devh:grpc-spring-boot-starter:$grpcSpringStarterVersion")

    api("org.apache.httpcomponents.client5:httpclient5:5.3.1")
}

protobuf{
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVersion"
    }
    plugins {
        id("grpc"){
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
        }
    }
    generateProtoTasks {
        all().forEach { task -> task.plugins{create("grpc") } }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
