package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

internal data class ListItemsWithRevision(
    @SerializedName("status")
    val status: String,
    @SerializedName("list")
    val list: List<NetworkTodoItem>,
    @SerializedName("revision")
    val revision: Int
)
