package com.flasshka.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object RetrofitBuilder {
    private const val BASE_URL = "https://hive.mrdekk.ru/todo/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val networkService: TodoListService = retrofit.create(TodoListService::class.java)
}