package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.ui.theme.DarkThemeGreen
import com.flasshka.todoapp.ui.theme.DarkThemeRed
import com.flasshka.todoapp.ui.theme.LightThemeGreen
import com.flasshka.todoapp.ui.theme.LightThemeRed
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeItemUI(
    item: TodoItem,
    getAction: (ListOfItemsActionType) -> (() -> Unit),

    modifier: Modifier = Modifier
) {
    val curState = rememberUpdatedState(newValue = item)
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            getConfirmAction(it, getAction, curState).invoke()
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
private fun getConfirmAction(
    it: SwipeToDismissBoxValue,
    getAction: (ListOfItemsActionType) -> () -> Unit,
    cur: State<TodoItem>
): () -> Unit {
    return when (it) {
        SwipeToDismissBoxValue.StartToEnd ->
            getAction(ListOfItemsActionType.OnChangeDoneItem(cur.value.id))

        SwipeToDismissBoxValue.EndToStart ->
            getAction(ListOfItemsActionType.OnDeleteItem(cur.value.id))

        else -> {
            {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeBackgroundContent(
    state: SwipeToDismissBoxState
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(getColor(state))
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(R.drawable.baseline_done_24),
            contentDescription = "done_item"
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
            contentDescription = "delete_item"
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun getColor(state: SwipeToDismissBoxState): Color {
    return when (state.dismissDirection) {
        SwipeToDismissBoxValue.StartToEnd ->
            if (isSystemInDarkTheme()) DarkThemeGreen else LightThemeGreen

        SwipeToDismissBoxValue.EndToStart ->
            if (isSystemInDarkTheme()) DarkThemeRed else LightThemeRed

        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSwipeItemUI() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            val item = remember {
                TodoItem(
                    id = "123",
                    text = "text",
                    TodoItem.Importance.Important,
                    created = Calendar.getInstance().time,
                    deadLine = Calendar.getInstance().time
                )
            }

            SwipeItemUI(item = item, getAction = { {} })
        }
    }
}