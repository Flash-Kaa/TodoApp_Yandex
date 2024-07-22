package com.flasshka.domain.usecases.settings

import com.flasshka.domain.entities.Settings
import com.flasshka.domain.interfaces.settings.SettingsRepository
import com.flasshka.domain.usecases.runWithSupervisorInBackground

class UpdateSettingsUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(settings: Settings) {
        runWithSupervisorInBackground {
            repository.updateSettings(settings)
        }
    }
}