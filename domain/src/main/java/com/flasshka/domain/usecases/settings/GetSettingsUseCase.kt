package com.flasshka.domain.usecases.settings

import com.flasshka.domain.entities.Settings
import com.flasshka.domain.interfaces.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(
    private val repository: SettingsRepository
) {
    operator fun invoke(): Flow<Settings> {
        return repository.getCurrentSettings()
    }
}