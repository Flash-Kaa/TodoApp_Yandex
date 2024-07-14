package com.flasshka.domain.entities

/**
 * data class for token data
 */
data class Token(
    val value: String = "",
    val revision: Revision = Revision(0)
) {
    fun getOAuthTokenValue() = "OAuth $value"

    data class Revision(val revision: Int)
}
