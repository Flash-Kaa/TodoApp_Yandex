package com.flasshka.todoapp.ui.info.divkit

import android.content.Context
import org.json.JSONObject

class AssetReader(private val context: Context) {
    fun read(filename: String): JSONObject {
        val inputStream = context.assets.open(filename)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return JSONObject(String(buffer, Charsets.UTF_8))
    }
}