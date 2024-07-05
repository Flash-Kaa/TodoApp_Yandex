package com.flasshka.todoapp.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * App module for context with DI
 */
@Module
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = app
}