package com.flasshka.data.di.components

import com.flasshka.data.di.modules.TokenRepositoryModule
import com.flasshka.domain.interfaces.TokenRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TokenRepositoryModule::class])
interface TokenRepositoryComponent {
    fun getTokenRepository(): TokenRepository
}