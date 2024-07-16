package com.flasshka.todoapp.ui.settings

import com.flasshka.domain.entities.Settings

data class SettingsStateUI(
    val theme: Settings.AppTheme = Settings.AppTheme.System
) {
    companion object {
        fun Settings.toStateUI(): SettingsStateUI {
            return SettingsStateUI(
                theme = appTheme
            )
        }
    }

    fun toSettings(): Settings {
        return Settings(
            appTheme = theme
        )
    }
}
