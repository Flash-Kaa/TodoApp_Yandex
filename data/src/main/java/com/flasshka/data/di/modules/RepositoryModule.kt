package com.flasshka.data.di.modules

import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.database.DataSourceDB
import com.flasshka.data.network.NetworkDataSource
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for implicate repository
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun repository(
        networkDataSource: NetworkDataSource,
        dataSourceDB: DataSourceDB
    ): TodoItemRepository = NetWithDbRepository(networkDataSource, dataSourceDB)
}