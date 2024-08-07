plugins {
    id("conventionplugin.presentation")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("conventionplugin.tgplugin")
}

val TG_CHAT = providers.environmentVariable("TG_CHAT")
val TG_TOKEN = providers.environmentVariable("TG_TOKEN")


validateApkSize {
    // OFF: validateOFF.set(true)
    maxMbSize.set(15f)
}

lectureTgPlugin {
    token.set(TG_TOKEN)
    chatId.set(TG_CHAT)
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

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    // DivKit
    implementation(libs.div)
    implementation(libs.div.core)
    implementation(libs.div.json)
    implementation(libs.div.picasso)
    implementation(libs.div.compose.interop)


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