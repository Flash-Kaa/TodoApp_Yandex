package com.flasshka.data.network.entities

/**
 * Item for response
 */
data class ListItemsWithRevision(
    val status: String,
    val list: List<NetworkTodoItem>,
    val revision: Int
)
