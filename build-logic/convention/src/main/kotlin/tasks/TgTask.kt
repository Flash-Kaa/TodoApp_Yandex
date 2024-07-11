package tasks

import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TgTask @Inject constructor(
    private val tgApi: TgApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @get:Input
    abstract val variant: Property<String>

    @get:Input
    abstract val version: Property<String>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                runBlocking {
                    println("Build finished")

                    tgApi.sendMessage(
                        token = token.get(),
                        chatId = chatId.get(),
                        message = "Build finished"
                    )
                }

                runBlocking {
                    println("Sending file")

                    tgApi.sendFile(
                        file = it,
                        chatId = chatId.get(),
                        token = token.get(),
                        variant = variant.get(),
                        version = version.get()
                    )
                }
            }
    }
}