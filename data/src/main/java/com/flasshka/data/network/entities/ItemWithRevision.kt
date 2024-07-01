package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

internal data class ItemWithRevision(
    @SerializedName("status")
    val status: String,
    @SerializedName("element")
    val element: NetworkTodoItem,
    @SerializedName("revision")
    val revision: Int
)