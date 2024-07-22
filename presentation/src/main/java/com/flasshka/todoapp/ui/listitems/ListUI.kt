package com.flasshka.todoapp.ui.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.domain.entities.TodoItem
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.ui.listitems.elements.CreateFAB
import com.flasshka.todoapp.ui.listitems.elements.ListTitle
import com.flasshka.todoapp.ui.listitems.elements.SwipeItemUI
import com.flasshka.todoapp.ui.theme.TodoAppTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUI(
    snackbarHostState: SnackbarHostState,
    doneCount: Int,
    visibilityDoneON: Boolean,
    items: List<TodoItem>,
    getAction: (ListOfItemsActionType) -> (() -> Unit),
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                doneCount = doneCount,
                visibilityDoneON = visibilityDoneON,
                getAction = getAction
            )
        },
        floatingActionButton = { CreateFAB(onClick = getAction(ListOfItemsActionType.OnCreate)) },
        content = { LazyListContent(it, items, getAction) }
    )
}

@Composable
private fun LazyListContent(
    paddingValues: PaddingValues,
    items: List<TodoItem>,
    getAction: (ListOfItemsActionType) -> () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
    ) {
        items(
            items = items,
            key = { it.id },
        ) { item ->
            SwipeItemUI(
                item = item,
                getAction = getAction
            )
        }

        item { NewItem(getAction = getAction) }
    }
}

@Composable
private fun NewItem(getAction: (ListOfItemsActionType) -> () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = getAction(ListOfItemsActionType.OnCreate))
    ) {
        Text(
            text = stringResource(R.string.new_item),
            fontWeight = FontWeight(400),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(start = 32.dp, bottom = 24.dp, top = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    doneCount: Int,
    visibilityDoneON: Boolean,
    getAction: (ListOfItemsActionType) -> (() -> Unit),
) {
    LargeTopAppBar(
        title = {
            ListTitle(
                doneCount = doneCount,
                visibilityDoneON = visibilityDoneON,
                getAction = getAction,
                scrollBehavior = scrollBehavior,
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewListUI() {
    val list = listOf(
        TodoItem("1", "testItem", TodoItem.Importance.Low, Calendar.getInstance().time)
    )

    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListUI(
                snackbarHostState = remember { SnackbarHostState() },
                doneCount = 1,
                visibilityDoneON = true,
                items = list,
                getAction = { {} }
            )
        }
    }
}