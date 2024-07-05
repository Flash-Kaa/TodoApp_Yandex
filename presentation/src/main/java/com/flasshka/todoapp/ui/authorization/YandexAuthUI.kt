package com.flasshka.todoapp.ui.authorization

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.flasshka.data.token.AuthWebViewClient
import com.flasshka.domain.entities.Token
import com.flasshka.todoapp.actions.AuthorizationActions

@Composable
fun YandexAuthUI(
    url: String,
    getAction: (AuthorizationActions) -> (() -> Unit)
) {
    val factory: (Context) -> View = {
        getWebView(
            context = it,
            url = url,
            getAction = getAction
        )
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = factory
    )
}

@SuppressLint("SetJavaScriptEnabled")
private fun getWebView(
    context: Context,
    url: String,
    getAction: (AuthorizationActions) -> (() -> Unit)
): WebView {
    val onSuccess: (Token) -> Unit = {
        getAction(AuthorizationActions.OnGetAnswer(it)).invoke()
    }

    return WebView(context).apply {
        settings.javaScriptEnabled = true
        webViewClient = AuthWebViewClient(
            onSuccess = onSuccess,
            onErrorAction = getAction(AuthorizationActions.OnExitAuth)
        )
        loadUrl(url)
    }
}