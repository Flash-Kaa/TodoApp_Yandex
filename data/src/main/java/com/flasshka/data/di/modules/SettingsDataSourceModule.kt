package com.flasshka.data.di.modules

import android.content.Context
import com.flasshka.data.di.SettingsRepositorySubcomponentScope
import com.flasshka.data.di.modules.binds.SettingsDataSourceBindModule
import com.flasshka.data.settings.SettingsSPDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [SettingsDataSourceBindModule::class])
internal class SettingsDataSourceModule {
    @Provides
    @SettingsRepositorySubcomponentScope
    fun provideSettingsDataSource(context: Context) = SettingsSPDataSource(context)
}