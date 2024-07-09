package com.flasshka.data.network.entities

/**
 * Item for request
 */
internal data class BodyListItems(
    val list: List<NetworkTodoItem>,
    val status: String = "ok",
)