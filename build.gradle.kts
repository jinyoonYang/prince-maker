plugins {
	val springBootVersion = "2.7.18"
	val kotlinVersion = "1.9.20"
	java
	id("org.springframework.boot") version springBootVersion
	id("io.spring.dependency-management") version "1.1.4"
// kotlin jvm 플러그인
	kotlin("jvm") version kotlinVersion
// kotlin spring 호환성 (open class) 플러그인
	kotlin("plugin.spring") version kotlinVersion
// Kotlin Annotation Processing Tool 플러그인
	kotlin("kapt") version kotlinVersion
// kotlin jpa 호환성(entity class 에 매개변수 없는 기본생성자 생성)
	kotlin("plugin.jpa") version kotlinVersion
// kotlin에서 lombok을 사용할 수 있게 만드는 플러그인
	kotlin("plugin.lombok") version kotlinVersion
	id("io.freefair.lombok") version "8.1.0"
}
group = "com.makers"
version = "0.0.1-SNAPSHOT"
java {
	sourceCompatibility = JavaVersion.VERSION_11
}
repositories {
	mavenCentral()
}
dependencies {
	// querydsl
	val querydslVersion = "5.0.0"
	implementation("com.querydsl:querydsl-jpa:$querydslVersion")
	kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}
kapt {
	keepJavacAnnotationProcessors = true
}
tasks { test {
	useJUnitPlatform()
}
	compileKotlin {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "11"
		}
	}
	compileTestKotlin {
		kotlinOptions {
			jvmTarget = "11"
		} }
}