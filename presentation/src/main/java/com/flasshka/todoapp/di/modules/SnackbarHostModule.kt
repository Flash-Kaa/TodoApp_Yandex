package com.flasshka.todoapp.di.modules

import androidx.compose.material3.SnackbarHostState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SnackbarHostModule {
    @Provides
    @Singleton
    fun provideSnackbarHost(): SnackbarHostState = SnackbarHostState()
}