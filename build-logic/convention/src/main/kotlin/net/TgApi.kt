package net

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import java.io.File

class TgApi {
    private val client = HttpClient()

    suspend fun sendFile(
        file: File,
        chatId: String,
        token: String,
        variant: String,
        version: String
    ) {
        client.post("https://api.telegram.org/bot$token/sendDocument") {
            parameter("chat_id", chatId)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("document", file.readBytes(),
                            Headers.build {
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=todolist-$variant-$version.apk"
                                )
                            }
                        )
                    }
                )
            )
        }
    }

    suspend fun sendMessage(token: String, chatId: String, message: String) {
        client.post("https://api.telegram.org/bot$token/sendMessage") {
            parameter("chat_id", chatId)
            parameter("text", message)
        }
    }
}