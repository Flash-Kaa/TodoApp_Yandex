package com.flasshka.domain.entities

data class Settings(
    val appTheme: AppTheme
) {
    enum class AppTheme {
        Light, Dark, System;

        override fun toString(): String {
            return when (this) {
                Light -> "Светлая тема"
                Dark -> "Темная тема"
                System -> "Системная тема"
            }
        }
    }
}
