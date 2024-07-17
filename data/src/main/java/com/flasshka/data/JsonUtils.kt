package com.flasshka.data

import com.google.gson.Gson

object JsonUtils {
    private val mapper = Gson()

    fun <T> convertToJson(item: T): String = mapper.toJson(item)

    fun <T> convertFromJson(json: String, clazz: Class<T>): T = mapper.fromJson(json, clazz)
}