package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@Composable
fun NameField(
    name: String,
    getAction: (EditItemActionType) -> (() -> Unit),

    modifier: Modifier = Modifier
) {
    TextField(
        value = name,
        onValueChange = { getAction(EditItemActionType.OnNameChanged(it)).invoke() },
        placeholder = { Placeholder() },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.back_secondary),
            unfocusedContainerColor = colorResource(id = R.color.back_secondary),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 130.dp)
            .shadow(2.dp, RoundedCornerShape(10.dp))
    )
}

@Composable
private fun Placeholder() {
    Text(
        text = stringResource(R.string.what_write),
        fontSize = 16.sp,
        fontWeight = FontWeight(400),
        color = colorResource(id = R.color.label_tertiary)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewNameField() {
    var name: String by remember {
        mutableStateOf("")
    }

    TodoAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            NameField(
                name = name,
                getAction = { action ->
                    {
                        name = (action as EditItemActionType.OnNameChanged).newVale
                    }
                }
            )
        }
    }
}