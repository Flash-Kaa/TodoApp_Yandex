package com.flasshka.data.network.entities


data class ListItemsWithRevision(
    val status: String,
    val list: List<NetworkTodoItem>,
    val revision: Int
)
