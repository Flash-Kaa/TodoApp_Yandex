package com.flasshka.data.network.entities

internal data class BodyListItems(
    val items: List<NetworkTodoItem>,
    val status: String = "ok",
)