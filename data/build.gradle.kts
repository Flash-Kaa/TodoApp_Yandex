plugins {
    id("conventionplugin.data")
    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
}

dependencies {
    implementation(project(":domain"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Dagger 2
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    ksp(libs.dagger.android.processor)
    ksp(libs.dagger.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}