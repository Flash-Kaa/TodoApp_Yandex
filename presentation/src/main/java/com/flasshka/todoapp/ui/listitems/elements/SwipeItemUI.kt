package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.ListOfItemsActionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeItemUI(
    item: TodoItem,
    getAction: (ListOfItemsActionType) -> (() -> Unit),

    modifier: Modifier = Modifier
) {
    val cur = rememberUpdatedState(newValue = item)
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> getAction(
                    ListOfItemsActionType.OnChangeDoneItem(
                        id = cur.value.id
                    )
                ).invoke()

                SwipeToDismissBoxValue.EndToStart -> getAction(
                    ListOfItemsActionType.OnDeleteItem(
                        id = cur.value.id
                    )
                ).invoke()

                else -> {}
            }
            return@rememberSwipeToDismissBoxState false
        },
        positionalThreshold = { it * .25f }
    )

    SwipeToDismissBox(
        state = state,
        backgroundContent = { SwipeBackgroundContent(state) },
        modifier = modifier.fillMaxWidth()
    ) {
        ItemUI(
            item = item,
            getAction = getAction
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBackgroundContent(
    state: SwipeToDismissBoxState
) {
    val color = when (state.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd -> colorResource(id = R.color.green)
        SwipeToDismissBoxValue.EndToStart -> colorResource(id = R.color.red)
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            // make sure add baseline_archive_24 resource to drawable folder
            painter = painterResource(R.drawable.baseline_done_24),
            contentDescription = "done_item"
        )

        Spacer(modifier = Modifier)

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
            contentDescription = "delete_item"
        )
    }
}