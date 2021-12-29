import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
    kotlin("plugin.jpa") version "1.6.0"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

subprojects {
    group = "me.hyungil"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }

    }

    repositories {
        mavenCentral()
    }

    noArg {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    dependencies {
        dependencies {
            implementation("org.jetbrains.kotlin:kotlin-reflect")
            implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
            developmentOnly("org.springframework.boot:spring-boot-devtools")

            testImplementation("org.springframework.boot:spring-boot-starter-test")
        }
    }
}

extra["springCloudVersion"] = "2020.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.RELEASE")
    }
}

project(":eureka") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server:3.0.4")
    }
}

project(":core") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    }
}

project(":user") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.security:spring-security-core:5.6.0")

        testImplementation("io.mockk:mockk:1.12.0")
        testImplementation("io.kotest:kotest-runner-junit5:5.0.2")
        testImplementation("io.kotest:kotest-assertions-core:5.0.2")

        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

        implementation("org.flywaydb:flyway-mysql:8.2.1")
        runtimeOnly("mysql:mysql-connector-java")

        implementation("io.jsonwebtoken:jjwt:0.9.1")

        implementation(project(":core"))
    }
}

project(":auth") {
    dependencies {

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")

        implementation("org.springframework.boot:spring-boot-starter-security:2.5.4")

        implementation("io.jsonwebtoken:jjwt:0.9.1")

        implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.0.6")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

        implementation("org.flywaydb:flyway-mysql:8.2.1")
        runtimeOnly("mysql:mysql-connector-java")

        implementation("com.google.code.gson:gson:2.8.9")

        testImplementation("io.mockk:mockk:1.12.0")
        testImplementation("io.kotest:kotest-runner-junit5:5.0.2")
        testImplementation("io.kotest:kotest-assertions-core:5.0.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")

        implementation(project(":core"))
    }
}

project(":gateway") {
    dependencies {

        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        implementation("org.springframework.cloud:spring-cloud-starter-gateway:3.0.6")
        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }
}

project(":product") {
    dependencies {

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.1")

        implementation("org.springframework.kafka:spring-kafka:2.8.1")

        implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:3.0.4")

        implementation("org.flywaydb:flyway-mysql:8.2.1")
        runtimeOnly("mysql:mysql-connector-java")

        testImplementation("io.mockk:mockk:1.12.0")
        testImplementation("io.kotest:kotest-runner-junit5:5.0.2")
        testImplementation("io.kotest:kotest-assertions-core:5.0.2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        implementation(project(":core"))
    }
}

tasks {
    bootJar {
        enabled = false
    }
}

