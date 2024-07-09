package com.flasshka.todoapp

import android.app.Application
import com.flasshka.todoapp.di.components.AppComponent
import com.flasshka.todoapp.di.components.DaggerAppComponent
import com.flasshka.todoapp.di.modules.AppModule

class TodoApp : Application() {
    lateinit var component: AppComponent

    private fun initDagger(app: TodoApp): AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()

    override fun onCreate() {
        super.onCreate()
        component = initDagger(this)
    }
}