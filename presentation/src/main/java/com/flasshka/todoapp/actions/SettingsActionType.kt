package com.flasshka.todoapp.actions

import com.flasshka.domain.entities.Settings

sealed class SettingsActionType {
    // Navigation
    data object OnExit : SettingsActionType()
    data object OnSave : SettingsActionType()
    data object GoToInfo : SettingsActionType()

    // State
    data class OnChangeAppTheme(val newTheme: Settings.AppTheme) : SettingsActionType()
}