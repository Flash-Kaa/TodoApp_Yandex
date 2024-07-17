import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import net.TgApi
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.register
import tasks.ApkUnzipTask
import tasks.TgTask
import tasks.ValidateTask

class LectureTgPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val androidComponent = extensions
                .findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("android plugin required")

            val tgExtension = extensions.create("lectureTgPlugin", TelegramExtension::class.java)
            val validateExtension =
                extensions.create("validateApkSize", ValidateSizeExtension::class.java)
            androidComponent.onVariants { variant: Variant ->
                val artifacts = variant.artifacts.get(SingleArtifact.APK)

                tasks.register(
                    "validateApkSizeFor${variant.name.capitalize()}",
                    ValidateTask::class,
                    TgApi()
                )
                    .configure {
                        maxMbSize.set(validateExtension.maxMbSize)
                        token.set(tgExtension.token)
                        chatId.set(tgExtension.chatId)
                        apkDir.set(artifacts)
                    }

                tasks.register("reportFor${variant.name.capitalize()}", TgTask::class, TgApi())
                    .configure {
                        if (validateExtension.validateOFF.isPresent.not()
                            || validateExtension.validateOFF.get().not()) {
                            dependsOn("validateApkSizeFor${variant.name.capitalize()}")
                        }

                        apkDir.set(artifacts)
                        token.set(tgExtension.token)
                        chatId.set(tgExtension.chatId)
                        this.variant.set(variant.name)

                        version.set(target.version.toString())
                    }

                tasks.register(
                    "spaceComponentsFor${variant.name.capitalize()}",
                    ApkUnzipTask::class,
                    TgApi()
                ).configure {
                    dependsOn("reportFor${variant.name.capitalize()}")
                    apkDir.set(artifacts)
                    token.set(tgExtension.token)
                    chatId.set(tgExtension.chatId)
                }
            }
        }
    }
}

interface TelegramExtension {
    val chatId: Property<String>
    val token: Property<String>
}

interface ValidateSizeExtension {
    val maxMbSize: Property<Float>
    val validateOFF: Property<Boolean>
}