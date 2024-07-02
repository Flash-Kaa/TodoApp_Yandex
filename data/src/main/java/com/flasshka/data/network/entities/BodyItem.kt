package com.flasshka.data.network.entities

internal data class BodyItem(
    val element: NetworkTodoItem,
    val status: String = "ok"
)