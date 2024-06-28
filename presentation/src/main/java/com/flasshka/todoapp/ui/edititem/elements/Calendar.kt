package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.DarkThemeLabelDisable
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeLabelDisable
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    onCancel: () -> Unit,
    onDone: (Long?) -> Unit
) {
    val blue = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue

    val state = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                text = stringResource(R.string.done),
                onClick = {
                    onDone(state.selectedDateMillis)
                },
                enabled = state.selectedDateMillis != null
            )
        },
        dismissButton = {
            TextButton(
                text = stringResource(R.string.cancel),
                onClick = onCancel
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            selectedDayContainerColor = blue,
        )
    ) {
        DatePicker(
            state = state,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                selectedYearContainerColor = blue,
                selectedDayContainerColor = blue,
                dayInSelectionRangeContainerColor = blue,
                disabledSelectedDayContainerColor = blue,
            )
        )
    }
}

@Composable
private fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val color = if (enabled)
        if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue
    else
        if (isSystemInDarkTheme()) DarkThemeLabelDisable else LightThemeLabelDisable

    val clickable = if (enabled) modifier.clickable(onClick = onClick) else modifier

    Text(
        text = text,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        modifier = clickable.padding(16.dp),
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Calendar(
                onCancel = { },
                onDone = { }
            )
        }
    }
}