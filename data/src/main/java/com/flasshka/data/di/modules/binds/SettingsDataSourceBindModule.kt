package com.flasshka.data.di.modules.binds

import com.flasshka.data.di.SettingsRepositorySubcomponentScope
import com.flasshka.data.settings.SettingsSPDataSource
import com.flasshka.domain.interfaces.settings.SettingsDataSource
import dagger.Binds
import dagger.Module

@Module
internal abstract class SettingsDataSourceBindModule {
    @Binds
    @SettingsRepositorySubcomponentScope
    abstract fun bindSettingsSharedPreferencesDataSourceToInterface(
        dataSource: SettingsSPDataSource
    ): SettingsDataSource
}