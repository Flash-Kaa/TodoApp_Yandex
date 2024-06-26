package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun CreateFAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = modifier
    ) {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(end = 16.dp, bottom = 32.dp)
                .size(70.dp),
            containerColor = colorResource(id = R.color.blue),
            contentColor = colorResource(id = R.color.white),
            shape = RoundedCornerShape(35.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_add_24),
                contentDescription = "create_btn"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCreateFAB() {
    TodoAppTheme {
        CreateFAB(
            onClick = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}