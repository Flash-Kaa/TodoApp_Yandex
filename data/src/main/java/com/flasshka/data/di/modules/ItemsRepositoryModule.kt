package com.flasshka.data.di.modules

import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.di.DatabaseDataSourceQualifier
import com.flasshka.data.di.NetworkDataSourceQualifier
import com.flasshka.data.di.modules.binds.DataSourceBindModule
import com.flasshka.data.di.modules.binds.RepositoryBindModule
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.domain.interfaces.TodoItemDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [TodoItemsDataSourceModule::class, RepositoryBindModule::class])
class ItemsRepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(
        @NetworkDataSourceQualifier networkDataSource: TodoItemDataSource,
        @DatabaseDataSourceQualifier databaseDataSource: TodoItemDataSource,
    ): NetWithDbRepository = NetWithDbRepository(networkDataSource, databaseDataSource)
}