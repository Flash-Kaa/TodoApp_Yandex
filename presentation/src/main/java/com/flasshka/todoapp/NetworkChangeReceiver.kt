package com.flasshka.todoapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkChangeReceiver(
    private val onInternetConnectionChange: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, p1: Intent?) {
        onInternetConnectionChange(context != null && isOnline(context))
    }

    private fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network: Network? = manager.activeNetwork
        val networkCapabilities: NetworkCapabilities? = manager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}