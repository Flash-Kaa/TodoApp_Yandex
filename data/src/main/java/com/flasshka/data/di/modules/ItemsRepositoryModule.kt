package com.flasshka.data.di.modules

import com.flasshka.data.di.DatabaseDataSourceQualifier
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.NetworkDataSourceQualifier
import com.flasshka.data.di.modules.binds.RepositoryBindModule
import com.flasshka.data.items.database.DatabaseRepository
import com.flasshka.data.items.network.NetworkRepository
import com.flasshka.domain.interfaces.items.TodoItemDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [TodoItemsDataSourceModule::class, RepositoryBindModule::class])
class ItemsRepositoryModule {
    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideNetRepository(
        @NetworkDataSourceQualifier networkDataSource: TodoItemDataSource,
    ): NetworkRepository = NetworkRepository(networkDataSource)

    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideDbRepository(
        @DatabaseDataSourceQualifier dbDataSource: TodoItemDataSource,
    ): DatabaseRepository = DatabaseRepository(dbDataSource)
}