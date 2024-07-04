package com.flasshka.todoapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.flasshka.data.DataSyncWorker
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: TodoItemRepository

    private val networkChangeReceiver: NetworkChangeReceiver by lazy {
        NetworkChangeReceiver(repository, lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataSyncWorker.scheduleDataSyncWork(applicationContext)

        /*val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
        val launcher = registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions()
        launcher.launch(loginOptions)*/

        (application as TodoApp).component.inject(this)

        setContent {
            TodoAppTheme {
                NavGraph(repository)
            }
        }
    }

    /*private fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                *//*onSuccessAuth(result.token)*//*
            }
            is YandexAuthResult.Failure -> {
                val exc = result.exception
                exc.errors
                *//*onProccessError(result.exception)*//*
            }
            YandexAuthResult.Cancelled -> {*//*onCancelled()*//*
            }
        }
    }*/

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