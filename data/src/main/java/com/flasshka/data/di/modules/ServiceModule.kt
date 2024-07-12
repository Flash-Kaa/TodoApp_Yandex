package com.flasshka.data.di.modules

import android.content.Context
import androidx.room.Room
import com.flasshka.data.database.AppDatabase
import com.flasshka.data.database.TodoItemsDao
import com.flasshka.data.di.ItemsRepositorySubcomponentScope
import com.flasshka.data.network.ServiceConstants
import com.flasshka.data.network.TodoListService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Module for implicate services for data source
 */
@Module
internal class ServiceModule {
    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideTodoListService(): TodoListService = Retrofit.Builder()
        .baseUrl("https://hive.mrdekk.ru/todo/")
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build().create()

    @Provides
    @ItemsRepositorySubcomponentScope
    fun provideDao(context: Context): TodoItemsDao {
        val db = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "todo_yandex_database123"
        ).build()

        return db.getDao()
    }
    
    private fun getClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor {
            val requestWithHeaders = it.request()
                .newBuilder()
                .header("Authorization", ServiceConstants.OAthWithToken.getFullTokenValue())
                .header("X-Last-Known-Revision", ServiceConstants.lastKnownRevision.toString())
                .build()

            it.proceed(requestWithHeaders)
        }
        .build()
}