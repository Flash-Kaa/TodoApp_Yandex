package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R

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
            Row(modifier = Modifier.padding(bottom = 16.dp, end = 16.dp)) {
                TextButton(
                    text = stringResource(R.string.cancel),
                    onClick = onCancel,
                    modifier = Modifier.padding(end = 32.dp),
                )

                TextButton(
                    text = stringResource(R.string.done),
                    onClick = {
                        onDone(state.selectedDateMillis)
                    },
                    enabled = state.selectedDateMillis != null
                )
            }

        },
        colors = DatePickerDefaults.colors(
            containerColor = colorResource(id = R.color.back_secondary),
            selectedDayContainerColor = colorResource(id = R.color.blue),
        )
    ) {
        DatePicker(
            state = state,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = colorResource(id = R.color.back_secondary),
                selectedYearContainerColor = colorResource(id = R.color.blue),
                selectedDayContainerColor = colorResource(id = R.color.blue),
                dayInSelectionRangeContainerColor = colorResource(id = R.color.blue),
                disabledSelectedDayContainerColor = colorResource(id = R.color.blue),
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
        colorResource(id = R.color.blue)
    else
        colorResource(id = R.color.label_disable)

    Text(
        text = text,
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        modifier = if (enabled) modifier.clickable(onClick = onClick) else modifier,
        color = color
    )
}