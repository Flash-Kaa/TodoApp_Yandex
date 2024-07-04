package com.flasshka.data.di.modules

import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for implicate DataSources
 */
@Module
class DataSourcesModule {
    @Provides
    @Singleton
    fun networkDataSource(service: TodoListService): NetworkDataSource = NetworkDataSource(service)

    @Provides
    @Singleton
    fun databaseDataSource(dao: TodoItemsDao): DataSourceDB = DataSourceDB(dao)
}