package com.flasshka.todoapp.di.components

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.flasshka.todoapp.MainActivity
import com.flasshka.todoapp.di.modules.SnackbarHostModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SnackbarHostModule::class])
internal interface AppComponent {
    fun inject(target: MainActivity)

    fun tokenRepositoryComponent(): TokenRepositorySubcomponent

    fun itemsRepositoryComponent(): ItemsRepositorySubcomponent

    /*fun provideEditItemVmFactory(): EditItemVM.FactoryWrapperWithUseCases

    fun provideListVmFactory(): ListVM.FactoryWrapperWithUseCases

    fun provideAuthVmFactory(): AuthorizationVM.FactoryWrapperWithUseCases*/

    fun provideSnackbar(): SnackbarHostState

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
