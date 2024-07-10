package com.flasshka.data.di.modules.binds

import com.flasshka.data.NetWithDbRepository
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBindModule {
    @Binds
    @ItemsRepositorySubcomponentScope
    abstract fun bindNetWithDbRepositoryToInterface(
        netWithDb: NetWithDbRepository
    ): TodoItemRepository
}