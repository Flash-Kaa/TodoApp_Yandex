package com.flasshka.todoapp.ui.listitems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListUI(
    doneCount: Int,
    visibilityDoneON: Boolean,
    items: List<TodoItem>,
    getAction: (ListOfItemsActionType) -> (() -> Unit)
) {
    val scrollState = rememberLazyListState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        canScroll = { scrollState.canScrollForward || scrollState.canScrollBackward }
    )

    scrollBehavior.state.collapsedFraction

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    ListTitle(
                        doneCount = doneCount,
                        visibilityDoneON = visibilityDoneON,
                        getAction = getAction,
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier.padding(start = 48.dp, end = 16.dp)
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            var isFirst = true

            items(items) { item ->
                val modifier = if (isFirst)
                    Modifier.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                else Modifier

                if (isFirst) {
                    isFirst = false
                }

                SwipeItemUI(
                    item = item,
                    getAction = getAction,
                    modifier = modifier
                )
            }

            item {
                val modifier = if (isFirst)
                    Modifier.clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                else
                    Modifier

                Box(
                    modifier = modifier
                        .clickable(onClick = getAction(ListOfItemsActionType.OnCreate))
                        .clip(RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.back_secondary))
                ) {
                    Text(
                        text = stringResource(R.string.new_item),
                        fontWeight = FontWeight(400),
                        fontSize = 16.sp,
                        color = colorResource(id = R.color.label_tertiary),
                        modifier = Modifier.padding(start = 32.dp, bottom = 24.dp, top = 8.dp)
                    )
                }
            }
        }
    }

    CreateFAB(
        onClick = getAction(ListOfItemsActionType.OnCreate)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewListUI() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colorResource(id = R.color.back_primary)
        ) {
            /*ListUI(
                { 3 },
                { false },
                {
                    listOf(
                        TodoItem(
                            id = "1",
                            text = "testItem",
                            importance = TodoItem.Importance.Low,
                            created = Calendar.getInstance().time,
                            deadLine = Calendar.getInstance().time,
                            completed = true
                        ),
                        TodoItem(
                            id = "1",
                            text = "testItem",
                            importance = TodoItem.Importance.Low,
                            created = Calendar.getInstance().time,
                            deadLine = Calendar.getInstance().time
                        ),
                        TodoItem(
                            id = "1",
                            text = "testItem",
                            importance = TodoItem.Importance.Low,
                            created = Calendar.getInstance().time,
                            deadLine = Calendar.getInstance().time
                        ),
                    )
                },
                getAction = { {} }
            )*/
        }
    }
}