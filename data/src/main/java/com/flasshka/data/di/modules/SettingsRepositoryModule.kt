package com.flasshka.data.di.modules

import com.flasshka.data.di.SettingsRepositorySubcomponentScope
import com.flasshka.data.di.modules.binds.SettingsRepositoryBindModule
import com.flasshka.data.settings.SettingsRepositoryImpl
import com.flasshka.domain.interfaces.settings.SettingsDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [SettingsRepositoryBindModule::class, SettingsDataSourceModule::class])
class SettingsRepositoryModule {
    @Provides
    @SettingsRepositorySubcomponentScope
    fun provideSettingsRepository(
        dataSource: SettingsDataSource
    ): SettingsRepositoryImpl = SettingsRepositoryImpl(dataSource)
}