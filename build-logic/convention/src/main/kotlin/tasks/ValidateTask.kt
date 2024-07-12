package tasks

import kotlinx.coroutines.runBlocking
import net.TgApi
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ValidateTask @Inject constructor(
    private val tgApi: TgApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val maxMbSize: Property<Float>

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                val sizeInMb = it.length().toFloat() / (1 shl 20)

                if (sizeInMb > maxMbSize.get()) {
                    exception(sizeInMb)
                } else {
                    successful(sizeInMb)
                }
            }
    }

    private fun exception(sizeInMb: Float) {
        val message = String.format("ERROR!\nA validation for the maximum " +
                ".apk file size of ${maxMbSize.get()} MB has been set, " +
                "but the file size is %.2f MB.", sizeInMb)
        runBlocking {
            tgApi.sendMessage(
                token = token.get(),
                chatId = chatId.get(),
                message = message
            )
        }

        throw GradleException(message)
    }

    private fun successful(sizeInMb: Float) {
        runBlocking {
            tgApi.sendMessage(
                token = token.get(),
                chatId = chatId.get(),
                message = String.format("Currect size is %.2f MB.", sizeInMb)
            )
        }
    }
}