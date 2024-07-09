package com.flasshka.data.token

import com.flasshka.todo.data.BuildConfig


object PathToGetToken {
    private const val PATH = "https://oauth.yandex.com/authorize"
    private const val RESPONSE_TYPE_PARAM = "response_type=token"
    private const val CLIENT_ID_PARAM = "client_id=${BuildConfig.CLIENT_ID}"
    private const val REDIRECT_URI_PARAM = "redirect_uri=${BuildConfig.REDIRECT_URI}"

    const val URI: String = "$PATH?$RESPONSE_TYPE_PARAM&$CLIENT_ID_PARAM&$REDIRECT_URI_PARAM"
}