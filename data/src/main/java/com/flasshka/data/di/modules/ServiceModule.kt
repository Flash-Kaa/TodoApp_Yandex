package com.flasshka.data.di.modules

import android.content.Context
import androidx.room.Room
import com.flasshka.data.database.AppDatabase
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * Module for implicate services for data source
 */
@Module
internal class ServiceModule {
    @Singleton
    @Provides
    fun provideTodoListService(): TodoListService = Retrofit.Builder()
        .baseUrl("https://hive.mrdekk.ru/todo/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()

    @Singleton
    @Provides
    fun provideDao(context: Context): TodoItemsDao {
        val db = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "todo_yandex_db"
        ).build()

        return db.getDao()
    }
}