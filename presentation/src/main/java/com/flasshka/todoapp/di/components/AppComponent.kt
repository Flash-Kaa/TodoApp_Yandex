package com.flasshka.todoapp.di.components

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.flasshka.data.di.modules.ItemsRepositoryModule
import com.flasshka.data.di.modules.TokenRepositoryModule
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.todoapp.MainActivity
import com.flasshka.todoapp.di.modules.ViewModelFactoryModule
import com.flasshka.todoapp.ui.authorization.AuthorizationVM
import com.flasshka.todoapp.ui.edititem.EditItemVM
import com.flasshka.todoapp.ui.listitems.ListVM
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, ItemsRepositoryModule::class, TokenRepositoryModule::class, ViewModelFactoryModule::class])
internal interface AppComponent {
    fun inject(target: MainActivity)

    fun provideItemsRepository(): TodoItemRepository

    fun provideTokenRepository(): TokenRepository

    fun provideEditItemVmFactory(): EditItemVM.FactoryWrapperWithUseCases

    fun provideListVmFactory(): ListVM.FactoryWrapperWithUseCases

    fun provideAuthVmFactory(): AuthorizationVM.FactoryWrapperWithUseCases

    fun provideSnackbar(): SnackbarHostState

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
