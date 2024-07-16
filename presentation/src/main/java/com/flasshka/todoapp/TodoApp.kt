package com.flasshka.todoapp

import android.app.Application
import com.flasshka.domain.interfaces.token.TokenRepository
import com.flasshka.todoapp.di.components.AppComponent
import com.flasshka.todoapp.di.components.DaggerAppComponent

internal class TodoApp : Application() {
    lateinit var appComponent: AppComponent
    lateinit var tokenRepository: TokenRepository

    private fun initDagger(app: TodoApp): AppComponent =
        DaggerAppComponent.builder()
            .context(app)
            .build()

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
        tokenRepository = appComponent
            .tokenRepositoryComponent()
            .provideTokenRepository()
    }
}