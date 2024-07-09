package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import com.flasshka.todoapp.ui.theme.White

@Composable
fun CreateFAB(
    onClick: () -> Unit,
) {
    val blue = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .size(70.dp),
        containerColor = blue,
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