package tasks

import kotlinx.coroutines.runBlocking
import net.TgApi
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.util.zip.ZipFile
import javax.inject.Inject

abstract class ApkUnzipTask @Inject constructor(
    private val tgApi: TgApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun execute() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                val zipFile = ZipFile(it)
                val tempFile = File.createTempFile("components", ".txt")

                tempFile.printWriter().use { writer ->
                    zipFile.entries().asSequence().forEach { entry ->
                        writer.write("${entry.name} ${getSizeName(entry.size)}")
                        writer.println()
                    }
                }

                runBlocking {
                    tgApi.sendFile(
                        token = token.get(),
                        chatId = chatId.get(),
                        file = tempFile,
                        filename = "space-stats.txt"
                    )
                }

                zipFile.close()
                tempFile.delete()
            }
    }

    private fun getSizeName(size: Long): String {
        val postfix = listOf("B", "KB", "MB", "GB", "TB.. WHAT?!")

        var curSize = size.toFloat()
        var postfixInd = 0

        while (curSize >= 1024) {
            curSize /= 1024
            postfixInd++
        }

        if (postfixInd >= postfix.size) {
            return "ERROR"
        }

        return String.format("%.2f ${postfix.get(postfixInd)}", curSize)
    }
}