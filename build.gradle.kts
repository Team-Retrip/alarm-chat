import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("kapt") version "1.9.25" // 추가

    id("org.springframework.boot") version "4.0.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.retrip"
version = "0.0.1-SNAPSHOT"
description = "retrip alarm service"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


sourceSets {
	main {
		java {
			srcDirs("src/main/kotlin", "src/main/java")
		}
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	// Spring Boot Starters
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.15"){
        // QueryDSL과 Spring Data 연동 시 발생하는 클래스 로딩 문제를 차단하기 위해 제외
        exclude(group = "org.querydsl")
        exclude(group = "org.springframework.data", module = "spring-data-commons")
    }
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.8.5") {
        // QueryDSL과 Spring Data 연동 시 발생하는 클래스 로딩 문제를 차단하기 위해 제외
        exclude(group = "org.querydsl")
        exclude(group = "org.springframework.data", module = "spring-data-commons")
    }
        // Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Database
	runtimeOnly("com.h2database:h2")
	// runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta") // annotationProcessor → kapt로 변경


    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	
	// Firebase
	implementation("com.google.firebase:firebase-admin:9.2.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
	}
}


tasks.withType<Test> {
    useJUnitPlatform()
}


sourceSets {
    val main by getting {
        java.srcDir("build/generated/source/kapt/main")
    }
}
