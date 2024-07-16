package com.flasshka.data.di.modules.binds

import com.flasshka.data.di.SettingsRepositorySubcomponentScope
import com.flasshka.data.settings.SettingsRepositoryImpl
import com.flasshka.domain.interfaces.settings.SettingsRepository
import dagger.Binds
import dagger.Module

@Module
internal abstract class SettingsRepositoryBindModule {
    @Binds
    @SettingsRepositorySubcomponentScope
    abstract fun bindSettingsRepositoryToInterface(
        repository: SettingsRepositoryImpl
    ): SettingsRepository
}