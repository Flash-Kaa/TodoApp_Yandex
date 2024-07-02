package com.flasshka.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.flasshka.data.TodoItemRepositoryImpl
import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository: TodoItemRepository = TodoItemRepositoryImpl(
            networkDataSource = NetworkDataSource.create(),
            databaseDataSource = DataSourceDB.create(applicationContext)
        )

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
}