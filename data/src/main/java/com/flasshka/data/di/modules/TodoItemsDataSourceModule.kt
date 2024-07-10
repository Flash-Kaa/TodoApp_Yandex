package com.flasshka.data.di.modules

import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.modules.binds.DataSourceBindModule
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides

@Module(includes = [ServiceModule::class, DataSourceBindModule::class])
internal class TodoItemsDataSourceModule {
    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideDatabaseDataSource(dao: TodoItemsDao): DataSourceDB = DataSourceDB(dao)

    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideNetworkDataSource(
        service: TodoListService
    ): NetworkDataSource = NetworkDataSource(service)
}