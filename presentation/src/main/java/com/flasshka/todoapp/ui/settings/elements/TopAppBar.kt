package com.flasshka.todoapp.ui.settings.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flasshka.todoapp.R
import com.flasshka.todoapp.actions.SettingsActionType
import com.flasshka.todoapp.ui.theme.DarkThemeBlue
import com.flasshka.todoapp.ui.theme.DarkThemeGray
import com.flasshka.todoapp.ui.theme.LightThemeBlue
import com.flasshka.todoapp.ui.theme.LightThemeGray

@Composable
fun TopAppBar(
    saveIsEnabled: () -> Boolean,
    getAction: (SettingsActionType) -> (() -> Unit)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(0.5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_close_24),
            contentDescription = "back to list",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true, color = Color.Gray),
                    onClick = getAction(SettingsActionType.OnExit)
                )
                .padding(16.dp)
                .size(30.dp)
        )

        SaveText(
            getAction = getAction,
            saveIsEnabled = saveIsEnabled
        )
    }
}

@Composable
private fun SaveText(
    saveIsEnabled: () -> Boolean,
    getAction: (SettingsActionType) -> () -> Unit
) {
    val color = if (saveIsEnabled()) if (isSystemInDarkTheme()) DarkThemeBlue else LightThemeBlue
    else if (isSystemInDarkTheme()) DarkThemeGray else LightThemeGray

    Text(
        text = stringResource(R.string.save),
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.Gray),
                onClick = getAction(SettingsActionType.OnSave),
                enabled = saveIsEnabled()
            )
            .padding(16.dp),
        fontWeight = FontWeight(500),
        fontSize = 16.sp,
        color = color,
        style = MaterialTheme.typography.bodyMedium
    )
}
