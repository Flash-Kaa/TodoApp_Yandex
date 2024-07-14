package com.flasshka.data.di.modules.binds

import com.flasshka.data.database.DatabaseRepository
import com.flasshka.data.di.DatabaseRepositoryQualifier
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.NetworkRepositoryQualifier
import com.flasshka.data.network.NetworkRepository
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBindModule {
    @Binds
    @DatabaseRepositoryQualifier
    @ItemsRepositorySubcomponentScope
    abstract fun bindDbRepositoryToInterface(
        repository: DatabaseRepository
    ): TodoItemRepository

    @Binds
    @NetworkRepositoryQualifier
    @ItemsRepositorySubcomponentScope
    abstract fun bindNetRepositoryToInterface(
        repository: NetworkRepository
    ): TodoItemRepository
}