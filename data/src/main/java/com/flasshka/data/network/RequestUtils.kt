package com.flasshka.data.network

import com.flasshka.data.JsonUtils.convertToJson
import okhttp3.MediaType
import okhttp3.RequestBody

internal object RequestUtils {
    fun <T> getBody(item: T, mediaType: String = "application/json"): RequestBody =
        RequestBody.create(MediaType.parse(mediaType), convertToJson(item))
}