package com.flasshka.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.flasshka.domain.entities.Settings
import com.flasshka.domain.interfaces.settings.SettingsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsSPDataSource(context: Context) : SettingsDataSource {
    private val sharedPreferenceName: String = "app_prefs"
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow<Settings>(
        Settings(
            appTheme = getAppTheme()
        )
    )

    val settings = _settings.asStateFlow()

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == SharedPreferenceKeys.AppTheme.name) {
                _settings.update { Settings(appTheme = getAppTheme()) }
            }
        }
    }

    override fun getCurrentSettings(): Flow<Settings> {
        return settings
    }

    override suspend fun updateSettings(settings: Settings) {
        _settings.update { settings }

        val key = SharedPreferenceKeys.AppTheme.name
        val value = settings.appTheme.name

        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun getAppTheme(): Settings.AppTheme {
        val key = SharedPreferenceKeys.AppTheme.name
        val defaultValue = Settings.AppTheme.System.name
        val value = sharedPreferences.getString(key, defaultValue)

        return Settings.AppTheme.valueOf(value ?: defaultValue)
    }

    enum class SharedPreferenceKeys {
        AppTheme
    }
}