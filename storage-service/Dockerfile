FROM gradle:8.14-jdk17-jammy AS builder
WORKDIR /build

COPY . .

RUN gradle :storage-service:build -x test

FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

COPY --from=builder /build/storage-service/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]