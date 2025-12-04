plugins {
    kotlin("jvm") version "1.9.22"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22" 
}

subprojects {
    if (listOf("applications", "components", "support").contains(name)) return@subprojects

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.slf4j:slf4j-api:2.0.7")
dependencies {
    implementation("org.jetbrains.exposed:exposed-core:0.42.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.42.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.42.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
    implementation("io.ktor:ktor-server-core:2.3.4")
    implementation("io.ktor:ktor-server-netty:2.3.4")
}
        testImplementation(kotlin("test-junit"))
    }
}