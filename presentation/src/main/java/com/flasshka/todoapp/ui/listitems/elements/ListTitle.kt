package com.flasshka.todoapp.ui.listitems.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.ListOfItemsActionType

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
        Column {
            // 100% :   collapseFraction == 1   this is min size of topAppBar
            // 0%   :   collapseFraction == 0   this is max size of topAppBar
            val collapseFraction = scrollBehavior.state.collapsedFraction
            val titleTextSize = (32 - collapseFraction * 12).sp

            Text(
                text = stringResource(R.string.my_work),
                color = colorResource(id = R.color.label_primary),
                fontWeight = FontWeight(500),
                fontSize = titleTextSize
            )

            if (collapseFraction < 0.63f) {
                Text(
                    text = stringResource(R.string.done_count, doneCount),
                    color = colorResource(id = R.color.label_tertiary),
                    fontWeight = FontWeight(400),
                    fontSize = 16.sp
                )

            }

        }

        val icon = if (visibilityDoneON)
            ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
        else
            ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)

        Icon(
            imageVector = icon,
            contentDescription = "visibility done icon",
            tint = colorResource(id = R.color.blue),
            modifier = Modifier
                .clickable(
                    onClick = getAction(ListOfItemsActionType.OnChangeDoneVisibility)
                )
                .padding(end = 16.dp)
        )
    }
}