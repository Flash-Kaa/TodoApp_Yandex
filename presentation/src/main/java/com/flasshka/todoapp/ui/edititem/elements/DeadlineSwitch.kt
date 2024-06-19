package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun DeadlineSwitch(
    checked: Boolean,
    getDate: () -> Long?,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        DeadlineText(
            getDate = getDate,
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
    getDate: () -> Long?,
) {
    Column {
        Text(
            text = stringResource(R.string.deadline_date),
            color = colorResource(id = R.color.label_primary),
            fontSize = 16.sp,
            fontWeight = FontWeight(400)
        )

        DeadlineDate(
            getDate = getDate,
            checked = checked
        )
    }
}

@Composable
private fun DeadlineDate(
    checked: Boolean,
    getDate: () -> Long?,
) {
    if (checked) {
        Text(
            text = getDate().toString(),
            color = colorResource(id = R.color.blue),
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
        )
    }
}

@Composable
private fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,

    modifier: Modifier = Modifier
) {
    val thumbOffset by animateDpAsState(targetValue = if (checked) 18.dp else 0.dp, label = "")

    val colorThumb = if (checked) colorResource(id = R.color.blue)
    else colorResource(id = R.color.back_elevated)

    val colorTrack = if (checked) colorResource(id = R.color.blue).copy(alpha = 0.3f)
    else colorResource(id = R.color.overlay)

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
            DeadlineSwitch(true, { null }, {}, modifier = Modifier.padding(16.dp))
        }
    }
}