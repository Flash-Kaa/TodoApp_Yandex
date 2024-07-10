package com.flasshka.todoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.flasshka.todoapp.di.components.AppComponent
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
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context != null && isOnline(context)) {
            runUpdate(context)
        }
    }

    private fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities: NetworkCapabilities? =
            manager.getNetworkCapabilities(manager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    private fun runUpdate(context: Context) {
        coroutineScope.launch(Dispatchers.IO) {
            val component = context.appComponent

            if (needAuth(component)) {
                goToAuth(component)
            }

            component.itemsRepositoryComponent().provideItemsRepository().fetchItems()
        }
    }

    private suspend fun needAuth(component: AppComponent): Boolean {
        val tokenRepository = component.tokenRepositoryComponent().provideTokenRepository()
        tokenRepository.fetchToken()

        return tokenRepository.hasLogin.value.not()
    }

    private suspend fun goToAuth(component: AppComponent) {
        /* TODO val router = component.provideRouter()
        withContext(Dispatchers.Main.immediate) {
            router.navigateToAuthorization()
        }*/
    }
}