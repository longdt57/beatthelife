import org.gradle.kotlin.dsl.`kotlin-dsl`

repositories {
    jcenter()
}

plugins {
    kotlin("jvm") version "1.4.20"
    `java-gradle-plugin`
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("plugin_config") {
            id = "lee.group.beat"
            implementationClass = "AppPlugins"
        }
    }
}
