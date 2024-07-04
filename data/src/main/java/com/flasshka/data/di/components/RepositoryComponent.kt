package com.flasshka.data.di.components

import android.content.Context
import com.flasshka.data.di.modules.DataSourcesModule
import com.flasshka.data.di.modules.RepositoryModule
import com.flasshka.data.di.modules.ServiceModule
import dagger.Component

@Component(modules = [DataSourcesModule::class, RepositoryModule::class, ServiceModule::class])
interface RepositoryComponent {
    fun inject(context: Context)
}