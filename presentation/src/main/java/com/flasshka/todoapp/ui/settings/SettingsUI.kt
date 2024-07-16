package com.flasshka.todoapp.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.flasshka.todoapp.actions.SettingsActionType
import com.flasshka.todoapp.ui.settings.elements.InfoButton
import com.flasshka.todoapp.ui.settings.elements.ThemeChooser
import com.flasshka.todoapp.ui.settings.elements.TopAppBar

@Composable
fun SettingsUI(
    state: SettingsStateUI,
    snackbarHostState: SnackbarHostState,
    saveIsEnabled: () -> Boolean,
    getAction: (SettingsActionType) -> (() -> Unit)
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            TopAppBar(saveIsEnabled, getAction)
            Content(state, getAction)
        }
    }
}

@Composable
private fun Content(
    state: SettingsStateUI,
    getAction: (SettingsActionType) -> (() -> Unit)
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            ThemeChooser(state, getAction)
        }

        item {
            InfoButton(getAction = getAction)
        }
    }
}