package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.EditTodoItemState
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.DarkThemeOverlay
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeOverlay
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DeadlineSwitch(
    checked: Boolean,
    state: EditTodoItemState,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        DeadlineText(
            state = state,
            checked = checked
        )

        CustomSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun DeadlineText(
    checked: Boolean,
    state: EditTodoItemState
) {
    Column {
        Text(
            text = stringResource(R.string.deadline_date),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )

        DeadlineDate(
            state = state,
            checked = checked
        )
    }
}

@Composable
private fun DeadlineDate(
    checked: Boolean,
    state: EditTodoItemState
) {
    if (checked) {
        val context = LocalContext.current
        val formatter = android.text.format.DateFormat.getDateFormat(context)

        val color = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue

        state.deadLine?.let {
            Text(
                text = formatter.format(it),
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        }
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,

    modifier: Modifier = Modifier
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 18.dp else 0.dp,
        label = "DpAnimation"
    )

    val blue = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue
    val colorThumb = if (checked) blue else MaterialTheme.colorScheme.surfaceVariant
    val colorTrack = if (checked) blue.copy(alpha = 0.3f)
    else if (isSystemInDarkTheme()) DarkThemeOverlay
    else LightThemeOverlay

    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(colorTrack)
                .clickable { onCheckedChange(!checked) }
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .size(width = 36.dp, height = 16.dp)
        )

        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .shadow(2.dp, CircleShape)
                .size(26.dp)
                .clip(CircleShape)
                .background(colorThumb)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDeadlineSwitch() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            var checked: Boolean by remember {
                mutableStateOf(true)
            }

            DeadlineSwitch(
                checked,
                EditTodoItemState.getNewState(),
                { checked = it },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}