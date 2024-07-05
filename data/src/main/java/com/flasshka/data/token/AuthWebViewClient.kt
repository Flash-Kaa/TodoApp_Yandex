package com.flasshka.data.token

import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.flasshka.domain.entities.Token

/**
 * Processing the results of opening a WebView
 */
class AuthWebViewClient(
    private val onSuccess: (Token) -> Unit,
    private val onErrorAction: () -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        if (request?.url == null || isCorrectUri(request.url).not()) {
            return super.shouldOverrideUrlLoading(view, request)
        }

        val tokenValue = request.url.fragment
            ?.substringAfter("access_token=", "")
            ?.substringBefore("&")!!

        val token = Token(tokenValue)
        onSuccess(token)

        return true
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) = onErrorAction()

    private fun isCorrectUri(uri: Uri): Boolean {
        val startIsCorrect = uri.toString()
            .startsWith("https://oauth.yandex.ru/verification_code")

        val hasAccessTokenParam = uri.fragment
            ?.substringAfter("access_token=")
            ?.substringBefore("&") != null

        return startIsCorrect && hasAccessTokenParam
    }
}