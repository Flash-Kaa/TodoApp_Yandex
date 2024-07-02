package com.flasshka.data.network.entities


data class ItemWithRevision(
    val status: String,
    val element: NetworkTodoItem,
    val revision: Int
)