package com.flasshka.data.network.entities

import com.google.gson.annotations.SerializedName

/**
 * Item for response
 */
data class ListItemsWithRevision(
    @SerializedName("status") val status: String,
    @SerializedName("list") val list: List<NetworkTodoItem>,
    @SerializedName("revision") val revision: Int
)
