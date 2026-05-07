import org.gradle.kotlin.dsl.annotationProcessor
import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.testImplementation

plugins {
    kotlin("jvm") version "2.3.0"
    kotlin("plugin.spring") version "2.3.0"
    kotlin("kapt") version "1.9.25" // 추가
    kotlin("plugin.jpa") version "2.3.0"

    id("org.asciidoctor.jvm.convert") version "4.0.2"
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
val asciidoctorExt = configurations.create("asciidoctorExt") {
    extendsFrom(configurations["testImplementation"])
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

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
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

	// Swagger (SpringDoc OpenAPI)
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3")
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
val snippetsDir by tasks.registering {
    val dir = file("build/generated-snippets")
    outputs.dir(dir)
}

tasks.test {
    outputs.dir(snippetsDir)
}
tasks.asciidoctor {
    inputs.dir(snippetsDir)
    dependsOn(tasks.test)
}
