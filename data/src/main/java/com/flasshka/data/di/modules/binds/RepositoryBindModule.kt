package com.flasshka.data.di.modules.binds

import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryBindModule {
    @Binds
    @ItemsRepositorySubcomponentScope
    abstract fun bindNetWithDbRepositoryToInterface(
        netWithDb: NetWithDbRepository
    ): TodoItemRepository
}