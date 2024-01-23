plugins {
    kotlin("jvm") version "1.9.20"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    checkstyle
}

group = "com.sursindmitry"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

val springBootVersion = "3.2.1"
val postgresqlVersion = "42.7.1"
val liquibaseVersion = "4.25.1"


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")

    implementation("org.postgresql:postgresql:$postgresqlVersion")
    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    bootJar {
        archiveFileName.set("repair-hub-$version.jar")
        archiveClassifier = "boot"
    }

    jar {
        enabled = false
    }


    checkstyle {
        toolVersion = "10.12.5"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
