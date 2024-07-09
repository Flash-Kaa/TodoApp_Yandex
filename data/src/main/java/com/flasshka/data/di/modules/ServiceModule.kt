package com.flasshka.data.di.modules

import android.content.Context
import com.flasshka.data.database.DatabaseBuilder
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.network.RetrofitBuilder
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module for implicate services for data source
 */
@Module
class ServiceModule {
    @Singleton
    @Provides
    fun todoListService(): TodoListService = RetrofitBuilder.networkService

    @Singleton
    @Provides
    fun dao(context: Context): TodoItemsDao = DatabaseBuilder.getDatabase(context).getDao()
}