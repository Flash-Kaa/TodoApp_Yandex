package com.flasshka.data.network.entities

/**
 * Item for response
 */
data class ItemWithRevision(
    val status: String,
    val element: NetworkTodoItem,
    val revision: Int
)