import java.util.Properties

plugins {
    id("conventionplugin.presentation")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("conventionplugin.tgplugin")
}

lectureTgPlugin {
    val localProperties = Properties()
    localProperties.load(project.rootProject.file("local.properties").inputStream())

    localProperties.getProperty("tg.token")?.let {
        token.set(it)
    }

    localProperties.getProperty("tg.chatID")?.let {
        chatId.set(it)
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))


    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Dagger 2
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    ksp(libs.dagger.android.processor)
    ksp(libs.dagger.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}