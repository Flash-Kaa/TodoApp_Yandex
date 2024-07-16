package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.BlueColor
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import com.flasshka.todoapp.ui.theme.White

@Composable
fun CreateFAB(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .size(70.dp)
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
            ),
        containerColor = BlueColor,
        contentColor = White,
        shape = RoundedCornerShape(35.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_24),
            contentDescription = "create_btn"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreateFAB() {
    TodoAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CreateFAB(
                onClick = { },
            )
        }
    }
}