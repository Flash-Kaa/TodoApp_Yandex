package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.R

@Composable
fun Underline(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(0.5.dp)
            .background(colorResource(id = R.color.separator))
            .fillMaxWidth()
    )
}