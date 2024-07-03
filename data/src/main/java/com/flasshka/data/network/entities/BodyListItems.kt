package com.flasshka.data.network.entities

internal data class BodyListItems(
    val list: List<NetworkTodoItem>,
    val status: String = "ok",
)