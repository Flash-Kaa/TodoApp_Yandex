package com.flasshka.todoapp.di.components

import com.flasshka.domain.usecases.settings.GetSettingsUseCase
import com.flasshka.domain.usecases.settings.UpdateSettingsUseCase
import com.flasshka.todoapp.di.SettingsUseCasesScope
import com.flasshka.todoapp.di.modules.SettingsUseCasesModule
import dagger.Subcomponent

@SettingsUseCasesScope
@Subcomponent(modules = [SettingsUseCasesModule::class])
internal interface SettingsUseCasesSubcomponent {
    fun settingsViewModelComponent(): SettingsViewModelSubcomponent

    fun getSettingsUseCase(): GetSettingsUseCase

    fun updateSettingsUseCase(): UpdateSettingsUseCase
}