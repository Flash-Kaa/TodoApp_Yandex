package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.ui.theme.DarkThemeSeparator
import com.flasshka.todoapp.ui.theme.LightThemeSeparator
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun Underline(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(0.5.dp)
            .background(if (isSystemInDarkTheme()) DarkThemeSeparator else LightThemeSeparator)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewUnderline() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Underline()
        }
    }
}