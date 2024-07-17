package com.flasshka.data.di.modules

import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.di.DatabaseDataSourceQualifier
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.NetworkDataSourceQualifier
import com.flasshka.domain.interfaces.TodoItemDataSource
import dagger.Module
import dagger.Provides

@Module(includes = [TodoItemsDataSourceModule::class])
class ItemsRepositoryModule {
    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideRepository(
        @NetworkDataSourceQualifier networkDataSource: TodoItemDataSource,
        @DatabaseDataSourceQualifier databaseDataSource: TodoItemDataSource,
    ): NetWithDbRepository = NetWithDbRepository(networkDataSource, databaseDataSource)
}