import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
}

group = "com.flasshka.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("domainPlugin") {
            id = "conventionplugin.domain"
            implementationClass = "DomainPlugin"
        }
    }
    plugins {
        register("presentationPlugin") {
            id = "conventionplugin.presentation"
            implementationClass = "PresentationPlugin"
        }
    }
    plugins {
        register("dataPlugin") {
            id = "conventionplugin.data"
            implementationClass = "DataPlugin"
        }
    }
}