package com.flasshka.domain.entities

/**
 * data class for token data
 */
data class Token(
    val value: String = ""
) {
    fun getFullTokenValue() = "OAuth $value"
}
