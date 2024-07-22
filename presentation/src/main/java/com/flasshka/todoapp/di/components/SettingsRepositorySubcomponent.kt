package com.flasshka.todoapp.di.components

import com.flasshka.data.di.SettingsRepositorySubcomponentScope
import com.flasshka.data.di.modules.SettingsRepositoryModule
import com.flasshka.domain.interfaces.settings.SettingsRepository
import dagger.Subcomponent

@SettingsRepositorySubcomponentScope
@Subcomponent(modules = [SettingsRepositoryModule::class])
internal interface SettingsRepositorySubcomponent {
    fun settingsUseCasesComponent(): SettingsUseCasesSubcomponent

    fun settingsRepository(): SettingsRepository
}
