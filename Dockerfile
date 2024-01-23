FROM gradle:8.5-jdk17-alpine AS builder
WORKDIR /app

COPY /config /app/config
COPY /gradle /app/gradle
COPY build.gradle.kts /app
COPY settings.gradle.kts /app
COPY /src /app/src

RUN gradle clean build --no-daemon --stacktrace

FROM openjdk:17-alpine
WORKDIR /
COPY --from=builder /app/build/libs/repair-hub-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT java -jar app.jar