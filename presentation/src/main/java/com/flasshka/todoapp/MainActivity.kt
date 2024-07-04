package com.flasshka.todoapp

import android.content.ContentProvider
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthResult
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // Will replaced with DI (may be)
    private val repository: TodoItemRepository by lazy {
        NetWithDbRepository(
            networkDataSource = NetworkDataSource.create(),
            localDataSource = DataSourceDB.create(applicationContext)
        )
    }

    private val networkChangeReceiver: NetworkChangeReceiver by lazy {
        NetworkChangeReceiver(repository, lifecycleScope)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DataSyncWorker.scheduleDataSyncWork(applicationContext)

        /*val sdk = YandexAuthSdk.create(YandexAuthOptions(applicationContext))
        val launcher = registerForActivityResult(sdk.contract) { result -> handleResult(result) }
        val loginOptions = YandexAuthLoginOptions()
        launcher.launch(loginOptions)*/


        setContent {
            TodoAppTheme {
                NavGraph(repository)
            }
        }
    }
    private fun handleResult(result: YandexAuthResult) {
        when (result) {
            is YandexAuthResult.Success -> {
                /*onSuccessAuth(result.token)*/
            }
            is YandexAuthResult.Failure -> {
                val exc = result.exception
                exc.errors
                /*onProccessError(result.exception)*/
            }
            YandexAuthResult.Cancelled -> {/*onCancelled()*/
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