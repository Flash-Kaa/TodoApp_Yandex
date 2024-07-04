package com.flasshka.domain.entities

import java.util.Date

/**
 * Standard item
 */
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
        Basic,
        Low,
        Important;

        override fun toString(): String {
            return when (this) {
                Basic -> "Нет"
                Low -> "Низкий"
                Important -> "!! Высокий"
            }
        }
    }
}