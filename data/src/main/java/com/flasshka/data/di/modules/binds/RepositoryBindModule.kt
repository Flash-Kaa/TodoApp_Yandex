package com.flasshka.data.di.modules.binds

import com.flasshka.data.NetWithDbRepository
import com.flasshka.domain.interfaces.TodoItemRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal abstract class RepositoryBindModule {
    @Binds
    @Singleton
    abstract fun bindNetWithDbRepositoryToInterface(
        netWithDb: NetWithDbRepository
    ): TodoItemRepository
}