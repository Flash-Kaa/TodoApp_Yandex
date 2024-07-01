package com.flasshka.data.network

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType
import okhttp3.RequestBody

internal object RequestUtils {
    private val mapper = ObjectMapper()
    private val mediaType = MediaType.parse("application/json")

    private fun <T> convertToJson(item: T): String = mapper.writeValueAsString(item)

    fun <T> getBody(item: T): RequestBody =
        RequestBody.create(mediaType, convertToJson(item))
}