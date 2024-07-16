package com.flasshka.data.di.modules.binds

import com.flasshka.data.di.DatabaseDataSourceQualifier
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.NetworkDataSourceQualifier
import com.flasshka.data.items.database.DataSourceDB
import com.flasshka.data.items.network.NetworkDataSource
import com.flasshka.domain.interfaces.items.TodoItemDataSource
import dagger.Binds
import dagger.Module

@Module
internal abstract class DataSourceBindModule {
    @Binds
    @DatabaseDataSourceQualifier
    @ItemsRepositorySubcomponentScope
    abstract fun bindDatabaseDataSourceToInterface(dataSourceDB: DataSourceDB): TodoItemDataSource

    @Binds
    @NetworkDataSourceQualifier
    @ItemsRepositorySubcomponentScope
    abstract fun bindNetDataSourceToInterface(dataSourceNet: NetworkDataSource): TodoItemDataSource
}