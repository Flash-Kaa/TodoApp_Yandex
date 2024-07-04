package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.ListOfItemsActionType
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.TodoAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTitle(
    doneCount: Int,
    visibilityDoneON: Boolean,
    getAction: (ListOfItemsActionType) -> (() -> Unit),

    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Title(scrollBehavior, doneCount)
        VisibilityIcon(visibilityDoneON, getAction)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Title(
    scrollBehavior: TopAppBarScrollBehavior,
    doneCount: Int
) {
    Column {
        // 100% :   collapseFraction == 1   this is min size of topAppBar
        // 0%   :   collapseFraction == 0   this is max size of topAppBar
        val collapseFraction = scrollBehavior.state.collapsedFraction
        val titleTextSize = (32 - collapseFraction * 12).sp

        Text(
            text = stringResource(R.string.my_work),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight(500),
            fontSize = titleTextSize
        )

        CollapsedDoneCount(collapseFraction, doneCount)
    }
}

@Composable
private fun CollapsedDoneCount(collapseFraction: Float, doneCount: Int) {
    if (collapseFraction > 0.63f) return

    Text(
        text = stringResource(R.string.done_count, doneCount),
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight(400),
        fontSize = 16.sp
    )
}

@Composable
private fun VisibilityIcon(
    visibilityDoneON: Boolean,
    getAction: (ListOfItemsActionType) -> () -> Unit
) {
    val icon = if (visibilityDoneON)
        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
    else
        ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)

    Icon(
        imageVector = icon,
        contentDescription = "visibility done icon",
        tint = if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue,
        modifier = Modifier
            .padding(end = 16.dp)
            .clickable(
                onClick = getAction(ListOfItemsActionType.OnChangeDoneVisibility)
            )
            .padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PreviewListTitle() {
    TodoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ListTitle(
                doneCount = 0,
                visibilityDoneON = true,
                getAction = { {} },
                scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
            )
        }
    }
}