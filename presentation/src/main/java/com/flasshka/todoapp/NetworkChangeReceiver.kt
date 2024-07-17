package com.flasshka.todoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.interfaces.TokenRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Monitors internet connection and updates data
 */
class NetworkChangeReceiver(
    private val coroutineScope: CoroutineScope,
) : BroadcastReceiver() {
    var navigateToAuthorization: (() -> Unit)? = null

    private lateinit var itemRepository: TodoItemRepository
    private lateinit var tokenRepository: TokenRepository

    override fun onReceive(context: Context?, p1: Intent?) {
        if (context != null && isOnline(context)) {
            itemRepository = context.itemsRepository
            tokenRepository = context.tokenRepository

            runUpdate()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities: NetworkCapabilities? =
            manager.getNetworkCapabilities(manager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun runUpdate() {
        coroutineScope.launch(Dispatchers.IO) {
            if (needAuth()) {
                if (navigateToAuthorization == null) {
                    return@launch
                }

                navigateToAuth()
            }

            itemRepository.fetchItems()
        }
    }

    private suspend fun needAuth(): Boolean {
        tokenRepository.fetchToken()
        return tokenRepository.hasLogin.value.not()
    }

    private suspend fun navigateToAuth() {
        withContext(Dispatchers.Main.immediate) {
            navigateToAuthorization?.invoke()
        }
    }
}