package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

internal data class BodyItem(
    @SerializedName("element")
    val element: NetworkTodoItem,
    @SerializedName("status")
    val status: String = "ok"
)