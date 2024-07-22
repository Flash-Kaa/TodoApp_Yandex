package com.flasshka.todoapp.di.modules

import com.flasshka.domain.interfaces.settings.SettingsRepository
import com.flasshka.domain.usecases.settings.GetSettingsUseCase
import com.flasshka.domain.usecases.settings.UpdateSettingsUseCase
import com.flasshka.todoapp.di.SettingsUseCasesScope
import dagger.Module
import dagger.Provides

@Module
class SettingsUseCasesModule {
    @Provides
    @SettingsUseCasesScope
    fun provideGetSettingsUseCase(
        repository: SettingsRepository
    ): GetSettingsUseCase = GetSettingsUseCase(repository)

    @Provides
    @SettingsUseCasesScope
    fun provideUpdateSettingsUseCase(
        repository: SettingsRepository
    ): UpdateSettingsUseCase = UpdateSettingsUseCase(repository)
}