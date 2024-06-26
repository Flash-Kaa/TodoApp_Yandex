package com.flasshka.domain.entities

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val created: Date,
    val deadLine: Date? = null,
    val completed: Boolean = false,
    val lastChange: Date? = null
) {
    enum class Importance {
        Common,
        Low,
        Urgently;

        override fun toString(): String {
            return when (this) {
                Common -> "Нет"
                Low -> "Низкий"
                Urgently -> "!! Высокий"
            }
        }
    }
}
