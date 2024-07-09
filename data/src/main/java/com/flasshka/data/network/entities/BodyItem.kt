package com.flasshka.data.network.entities

/**
 * Item for request
 */
internal data class BodyItem(
    val element: NetworkTodoItem,
    val status: String = "ok"
)