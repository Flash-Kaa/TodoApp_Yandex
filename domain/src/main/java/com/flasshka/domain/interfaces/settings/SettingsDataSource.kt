package com.flasshka.domain.interfaces.settings

import com.flasshka.domain.entities.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {
    fun getCurrentSettings(): Flow<Settings>
    suspend fun updateSettings(settings: Settings)
}