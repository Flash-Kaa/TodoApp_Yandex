package com.flasshka.data.settings

import com.flasshka.domain.entities.Settings
import com.flasshka.domain.interfaces.settings.SettingsDataSource
import com.flasshka.domain.interfaces.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val dataSource: SettingsDataSource
) : SettingsRepository {
    override fun getCurrentSettings(): Flow<Settings> {
        return dataSource.getCurrentSettings()
    }

    override suspend fun updateSettings(settings: Settings) {
        dataSource.updateSettings(settings)
    }
}