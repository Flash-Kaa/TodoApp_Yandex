package com.flasshka.todoapp.di.components

import com.flasshka.todoapp.di.modules.AppModule
import com.flasshka.data.di.modules.DataSourcesModule
import com.flasshka.data.di.modules.RepositoryModule
import com.flasshka.data.di.modules.ServiceModule
import com.flasshka.todoapp.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataSourcesModule::class, RepositoryModule::class, ServiceModule::class])
interface AppComponent {
    fun inject(target: MainActivity)
}
