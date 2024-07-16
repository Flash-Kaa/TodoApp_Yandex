package com.flasshka.todoapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.flasshka.domain.entities.Settings
import kotlinx.coroutines.flow.map

@Composable
fun getDarkThemeState(): Boolean {
    val isSystemDark = isSystemInDarkTheme()
    val themeIsDark: Boolean by LocalContext.current.appComponent
        .settingsRepositoryComponent()
        .settingsUseCasesComponent()
        .getSettingsUseCase()
        .invoke()
        .map {
            when (it.appTheme) {
                Settings.AppTheme.Dark -> true
                Settings.AppTheme.Light -> false
                Settings.AppTheme.System -> isSystemDark
            }
        }
        .collectAsState(isSystemDark)

    return themeIsDark
}