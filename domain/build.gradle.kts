plugins {
    id("conventionplugin.domain")
    alias(libs.plugins.jetbrainsKotlinJvm)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
}