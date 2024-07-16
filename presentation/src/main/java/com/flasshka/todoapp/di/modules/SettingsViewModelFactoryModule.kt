package com.flasshka.todoapp.di.modules

import com.flasshka.domain.usecases.settings.GetSettingsUseCase
import com.flasshka.domain.usecases.settings.UpdateSettingsUseCase
import com.flasshka.todoapp.di.SettingsViewModelScope
import com.flasshka.todoapp.ui.settings.SettingsVM
import dagger.Module
import dagger.Provides

@Module
internal class SettingsViewModelFactoryModule {
    @Provides
    @SettingsViewModelScope
    fun provideSettingsVMFactory(
        getSettingsUseCase: GetSettingsUseCase,
        updateSettingsUseCase: UpdateSettingsUseCase
    ): SettingsVM.FactoryWrapperWithUseCases = SettingsVM.FactoryWrapperWithUseCases(
        getSettingsUseCase = getSettingsUseCase,
        updateSettingsUseCase = updateSettingsUseCase
    )
}