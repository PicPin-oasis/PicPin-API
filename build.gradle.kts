import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    kotlin("plugin.noarg") version "1.8.22"
}

group = "com.picpin"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.4")
    implementation("com.auth0:java-jwt:4.3.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    implementation("org.hibernate:hibernate-spatial:6.2.13.Final")
    runtimeOnly("com.mysql:mysql-connector-j:8.2.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
    implementation("com.github.f4b6a3:ulid-creator:5.2.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.5")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}
