plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

/**
 * مهم جداً:
 * نضيف المستودعات على مستوى المشروع كله
 */
allprojects {
    repositories {
        // مستودعات علي بابا (سريعة داخل سوريا)
        maven { url = uri("https://maven.aliyun.com/repository/public") }
        maven { url = uri("https://maven.aliyun.com/repository/central") }

        mavenLocal()
        mavenCentral()
        google()
    }
}

/**
 * ضبط المشاريـع الفرعية
 */
subprojects {

    apply(plugin = "kotlin")

    dependencies {
        // Logging
        implementation("org.slf4j:slf4j-api:2.0.7")

        // Exposed ORM + SQLite
        implementation("org.jetbrains.exposed:exposed-core:0.42.0")
        implementation("org.jetbrains.exposed:exposed-dao:0.42.0")
        implementation("org.jetbrains.exposed:exposed-jdbc:0.42.0")
        implementation("org.jetbrains.exposed:exposed-java-time:0.42.0")
        implementation("org.xerial:sqlite-jdbc:3.42.0.0")

        // Ktor Server
        implementation("io.ktor:ktor-server-core:2.3.4")
        implementation("io.ktor:ktor-server-netty:2.3.4")

        // Testing
        testImplementation(kotlin("test"))
    }
}
