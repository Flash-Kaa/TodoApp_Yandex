package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

/**
 * Item for request
 */
internal data class BodyListItems(
    @SerializedName("list") val list: List<NetworkTodoItem>,
    @SerializedName("status") val status: String = "ok",
)