package com.flasshka.todoapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.flasshka.todoapp.navigation.NavGraph
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Preview(showBackground = true)
@Composable
private fun AppThemePreview() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavGraph()
        }
    }
}