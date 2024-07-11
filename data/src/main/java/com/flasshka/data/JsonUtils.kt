package com.flasshka.data

import com.fasterxml.jackson.databind.ObjectMapper

object JsonUtils {
    private val mapper = ObjectMapper()

    fun <T> convertToJson(item: T): String = mapper.writeValueAsString(item)

    fun <T> convertFromJson(json: String, clazz: Class<T>): T = mapper.readValue(json, clazz)
}