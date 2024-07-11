import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.register
import tasks.TgApi
import tasks.TgTask

class LectureTgPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val androidComponent = extensions
                .findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("android plugin required")

            val extension = extensions.create("lectureTgPlugin", TelegramExtension::class.java)
            androidComponent.onVariants { variant: Variant ->
                println("Variant ${variant.name}")
                val artifacts = variant.artifacts.get(SingleArtifact.APK)


                tasks.register("reportFor${variant.name.capitalize()}", TgTask::class, TgApi())
                    .configure {
                        apkDir.set(artifacts)
                        token.set(extension.token)
                        chatId.set(extension.chatId)
                        this.variant.set(variant.name)

                        version.set(target.version.toString())
                    }
            }
        }
    }
}

interface TelegramExtension {
    val chatId: Property<String>
    val token: Property<String>
}