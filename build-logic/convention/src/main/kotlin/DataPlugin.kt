import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

class DataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                namespace = "com.flasshka.todo.data"
                compileSdk = 34

                defaultConfig {
                    minSdk = 26

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                    val localProperties = Properties()
                    localProperties.load(project.rootProject.file("local.properties").inputStream())

                    val clientId: String? = localProperties.getProperty("oath.clientID")
                    val redirectURI: String? = localProperties.getProperty("oath.redirectURI")
                    buildConfigField("String", "CLIENT_ID", clientId.toString())
                    buildConfigField("String", "REDIRECT_URI", redirectURI.toString())
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }

                tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "1.8"
                    }
                }

                buildFeatures {
                    buildConfig = true
                }
            }
        }
    }
}