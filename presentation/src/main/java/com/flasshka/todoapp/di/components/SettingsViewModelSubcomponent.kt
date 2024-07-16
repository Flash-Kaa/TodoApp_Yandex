package com.flasshka.todoapp.di.components

import com.flasshka.todoapp.di.SettingsViewModelScope
import com.flasshka.todoapp.di.modules.SettingsViewModelFactoryModule
import com.flasshka.todoapp.ui.settings.SettingsVM
import dagger.Subcomponent

@SettingsViewModelScope
@Subcomponent(modules = [SettingsViewModelFactoryModule::class])
internal interface SettingsViewModelSubcomponent {
    fun provideFactoryWrapper(): SettingsVM.FactoryWrapperWithUseCases
}