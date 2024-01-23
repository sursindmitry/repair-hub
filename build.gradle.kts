plugins {
    java
    id("org.springframework.boot") version "3.2.2"
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


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion")
}

tasks {
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
