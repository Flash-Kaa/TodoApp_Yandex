package com.flasshka.todoapp.ui.edititem.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.flasshka.todoapp.actions.EditItemActionType
import com.flasshka.todoapp.ui.edititem.EditTodoItemState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomSheet(
    state: EditTodoItemState,
    bottomSheetIsVisible: MutableState<Boolean>,
    getAction: (EditItemActionType) -> () -> Unit,
) {
    val offsetY = remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                bottomSheetIsVisible.value = false
            }
            .background(
                Color.Black.copy(
                    alpha = (300 - offsetY.floatValue * 20) / 300f * 0.5f
                )
            )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        BottomSheetDrawer(
            state = state,
            offsetY = offsetY,
            bottomSheetIsVisible = bottomSheetIsVisible,
            getAction = getAction
        )
    }

}

@Composable
private fun BottomSheetDrawer(
    state: EditTodoItemState,
    offsetY: MutableFloatState,
    bottomSheetIsVisible: MutableState<Boolean>,
    getAction: (EditItemActionType) -> () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            )
            .offset(x = 0.dp, y = offsetY.floatValue.dp)
            .height((300 - offsetY.floatValue * 20).dp)
            .pointerInput(offsetY, bottomSheetIsVisible),
    ) {
        ImportanceChooser(
            state = state,
            getAction = getAction,
            modifier = Modifier
                .padding(top = 32.dp)
                .padding(16.dp)
        )
    }
}

@Composable
private fun Modifier.pointerInput(
    offsetY: MutableFloatState,
    bottomSheetIsVisible: MutableState<Boolean>
): Modifier {
    val scope = rememberCoroutineScope()
    return this.then(
        Modifier.pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                changeScope(scope, offsetY, bottomSheetIsVisible, dragAmount)
                change.consume()
            }
        }
    )
}

private fun changeScope(
    scope: CoroutineScope,
    offsetY: MutableFloatState,
    bottomSheetIsVisible: MutableState<Boolean>,
    dragAmount: Offset
) {
    scope.launch {
        offsetY.floatValue += dragAmount.y / 50

        if (offsetY.floatValue < 0) {
            offsetY.floatValue = 0f
        } else if (300 - offsetY.floatValue * 20 < 20) {
            bottomSheetIsVisible.value = false
        }
    }
}