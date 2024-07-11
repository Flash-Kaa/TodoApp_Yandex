package com.flasshka.todoapp

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.flasshka.data.DataSyncWorker
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    private val networkChangeReceiver: NetworkChangeReceiver by lazy {
        NetworkChangeReceiver(lifecycleScope)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataSyncWorker.scheduleDataSyncWork(applicationContext)

        setContent {
            TodoAppTheme {
                NavGraph()
            }
        }
    }

    // Enable broadcast receiver
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)
    }

    // Disable broadcast receiver
    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}