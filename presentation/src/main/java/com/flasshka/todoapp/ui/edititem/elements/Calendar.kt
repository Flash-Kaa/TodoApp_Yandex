package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
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
import com.flasshka.todoapp.ui.theme.BlueColor
import com.flasshka.todoapp.ui.theme.DisableColor
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    onCancel: () -> Unit,
    onDone: (Long?) -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                text = stringResource(R.string.done),
                onClick = { onDone(state.selectedDateMillis) },
                enabled = state.selectedDateMillis != null
            )
        },
        dismissButton = { TextButton(stringResource(R.string.cancel), onCancel) },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            selectedDayContainerColor = BlueColor,
        ),
        content = { CalendarDatePicker(state) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CalendarDatePicker(
    state: DatePickerState
) {
    DatePicker(
        state = state,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background,
            selectedYearContainerColor = BlueColor,
            selectedDayContainerColor = BlueColor,
            dayInSelectionRangeContainerColor = BlueColor,
            disabledSelectedDayContainerColor = BlueColor,
        )
    )
}

@Composable
private fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val clickable = if (enabled) modifier.clickable(onClick = onClick) else modifier

    Text(
        text = text,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        modifier = clickable.padding(16.dp),
        color = if (enabled) BlueColor else DisableColor
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