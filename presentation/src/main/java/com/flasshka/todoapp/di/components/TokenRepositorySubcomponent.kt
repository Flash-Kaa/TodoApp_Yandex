package com.flasshka.todoapp.di.components

import android.content.Context
import com.flasshka.data.di.TokenRepositorySubcomponentScope
import com.flasshka.data.di.modules.TokenRepositoryModule
import com.flasshka.data.di.modules.binds.TokenRepositoryBindModule
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Subcomponent

@TokenRepositorySubcomponentScope
@Subcomponent(modules = [TokenRepositoryModule::class, TokenRepositoryBindModule::class])
internal interface TokenRepositorySubcomponent {
    fun inject(target: Context)

    fun tokenUseCasesComponent(): TokenUseCaseSubcomponents

    fun itemsRepositoryComponent(): ItemsRepositorySubcomponent

    fun provideTokenRepository(): TokenRepository
}