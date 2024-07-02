package com.flasshka.todoapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val repository: TodoItemRepository by lazy {
        NetWithDbRepository(
            networkDataSource = NetworkDataSource.create(),
            databaseDataSource = DataSourceDB.create(applicationContext)
        )
    }

    private val networkChangeReceiver: NetworkChangeReceiver by lazy {
        NetworkChangeReceiver {
            if (it) {
                lifecycleScope.launch {
                    repository.fetchItems()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataSyncWorker.scheduleDataSyncWork(applicationContext)

        setContent {
            TodoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(repository)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkChangeReceiver)
    }
}