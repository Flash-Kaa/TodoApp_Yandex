package com.flasshka.todoapp.ui

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun snackbarShow(
    message: String,
    snackbarHostState: SnackbarHostState,
    actionLabel: String? = null,
    onActionPerformed: (suspend () -> Unit)? = null
) {
    val result = snackbarHostState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
        duration = SnackbarDuration.Short,
        withDismissAction = true
    )

    if (actionLabel != null && onActionPerformed != null) {
        when (result) {
            SnackbarResult.ActionPerformed -> onActionPerformed()
            SnackbarResult.Dismissed -> {}
        }
    }
}