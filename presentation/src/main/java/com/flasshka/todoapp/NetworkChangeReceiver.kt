package com.flasshka.todoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.flasshka.domain.interfaces.TodoItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Monitors internet connection and updates data
 */
class NetworkChangeReceiver(
    private val repository: TodoItemRepository,
    private val coroutineScope: CoroutineScope,
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        if (context != null && isOnline(context)) {
            coroutineScope.launch {
                repository.fetchItems()
            }
        }
    }

    private fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities: NetworkCapabilities? =
            manager.getNetworkCapabilities(manager.activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}