package com.flasshka.data.di.modules

import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.di.modules.binds.DataSourceBindModule
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ServiceModule::class, DataSourceBindModule::class])
internal class TodoItemsDataSourceModule {
    @Provides
    @Singleton
    fun provideDatabaseDataSource(dao: TodoItemsDao): DataSourceDB = DataSourceDB(dao)

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        service: TodoListService
    ): NetworkDataSource = NetworkDataSource(service)
}