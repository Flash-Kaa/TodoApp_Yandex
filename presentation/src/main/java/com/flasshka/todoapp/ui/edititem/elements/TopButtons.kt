package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun TopButtons(
    getAction: (EditItemActionType) -> (() -> Unit),
    modifier: Modifier = Modifier
) {
    Box(contentAlignment = Alignment.TopCenter) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_close_24),
                contentDescription = "back to list",
                tint = colorResource(id = R.color.label_primary),
                modifier = Modifier
                    .clickable(onClick = getAction(EditItemActionType.OnExit))
                    .padding(16.dp)
                    .size(30.dp)
            )

            Text(
                text = stringResource(R.string.save),
                modifier = Modifier
                    .clickable(onClick = getAction(EditItemActionType.OnSave))
                    .padding(16.dp),
                fontWeight = FontWeight(500),
                fontSize = 16.sp,
                color = colorResource(id = R.color.blue)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTopButtons() {
    TodoAppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            TopButtons({ {} })
        }
    }
}