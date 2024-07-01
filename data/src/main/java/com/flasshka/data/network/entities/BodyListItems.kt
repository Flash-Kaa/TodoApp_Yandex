package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

internal data class BodyListItems(
    @SerializedName("list")
    val items: List<NetworkTodoItem>,
    @SerializedName("status")
    val status: String,
)