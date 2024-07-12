import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import net.TgApi
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.register
import tasks.ValidateTask

class ValidateApkSizePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.create("validateApkSize", ValidateSizeExtension::class.java)

            val androidComponent = extensions
                .findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("android plugin required")

            androidComponent.onVariants { variant: Variant ->
                val artifacts = variant.artifacts.get(SingleArtifact.APK)

                if (extension.on.isPresent && extension.on.get().not()) {
                    return@onVariants
                } else if (extension.token.isPresent.not() || extension.chatId.isPresent.not()) {
                    return@onVariants
                }

                tasks.register(
                    "validateApkSizeFor${variant.name.capitalize()}",
                    ValidateTask::class, TgApi()
                )
                    .configure {
                        maxMbSize.set(extension.maxMbSize)
                        token.set(extension.token)
                        chatId.set(extension.chatId)
                        apkDir.set(artifacts)

                    }
            }
        }
    }
}

interface ValidateSizeExtension {
    val maxMbSize: Property<Float>
    val on: Property<Boolean>
    val chatId: Property<String>
    val token: Property<String>
}