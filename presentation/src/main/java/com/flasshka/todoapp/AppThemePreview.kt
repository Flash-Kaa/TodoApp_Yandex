package com.flasshka.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.DarkThemeGray
import com.flasshka.todoapp.ui.theme.DarkThemeGrayLight
import com.flasshka.todoapp.ui.theme.DarkThemeGreen
import com.flasshka.todoapp.ui.theme.DarkThemeLabelDisable
import com.flasshka.todoapp.ui.theme.DarkThemeOverlay
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.DarkThemeSeparator
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeGray
import com.flasshka.todoapp.ui.theme.LightThemeGrayLight
import com.flasshka.todoapp.ui.theme.LightThemeGreen
import com.flasshka.todoapp.ui.theme.LightThemeLabelDisable
import com.flasshka.todoapp.ui.theme.LightThemeOverlay
import com.flasshka.todoapp.ui.theme.LightThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeSeparator
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import com.flasshka.todoapp.ui.theme.White

@Preview(showBackground = false)
@Composable
private fun AppThemePreview() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ColorColumn()
        }
    }
}

@Composable
private fun ColorColumn() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            ColorBox(if (isSystemInDarkTheme()) DarkThemeSeparator else LightThemeSeparator)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeOverlay else LightThemeOverlay)
        }

        Row {
            ColorBox(MaterialTheme.colorScheme.primary)
            ColorBox(MaterialTheme.colorScheme.secondary)
            ColorBox(MaterialTheme.colorScheme.tertiary)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeLabelDisable else LightThemeLabelDisable)
        }

        Row {
            ColorBox(if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeGreen else LightThemeGreen)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeGray else LightThemeGray)
            ColorBox(if (isSystemInDarkTheme()) DarkThemeGrayLight else LightThemeGrayLight)
            ColorBox(White)
        }

        Row {
            ColorBox(MaterialTheme.colorScheme.background)
            ColorBox(MaterialTheme.colorScheme.surface)
            ColorBox(MaterialTheme.colorScheme.surfaceVariant)
        }
    }
}


@Composable
private fun ColorBox(color: Color) {
    Box(
        modifier = Modifier
            .size(width = 60.dp, height = 60.dp)
            .background(color)
    )
}