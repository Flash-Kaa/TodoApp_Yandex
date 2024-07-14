package com.flasshka.todoapp.di.modules

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.flasshka.data.di.DatabaseRepositoryQualifier
import com.flasshka.data.di.NetworkRepositoryQualifier
import com.flasshka.domain.interfaces.TodoItemRepository
import com.flasshka.domain.interfaces.TokenRepository
import com.flasshka.domain.usecases.items.AddTodoItemUseCase
import com.flasshka.domain.usecases.items.DeleteTodoItemUseCase
import com.flasshka.domain.usecases.items.FetchItemsUseCase
import com.flasshka.domain.usecases.items.GetDoneCountUseCase
import com.flasshka.domain.usecases.items.GetItemsWithVisibilityUseCase
import com.flasshka.domain.usecases.items.GetTodoItemByIdOrNullUseCase
import com.flasshka.domain.usecases.items.UpdateTodoItemUseCase
import com.flasshka.todoapp.R
import com.flasshka.todoapp.di.ItemsUseCaseScope
import com.flasshka.todoapp.ui.snackbarShow
import dagger.Module
import dagger.Provides

@Module(includes = [])
class ItemsUseCasesModule {
    @Provides
    @ItemsUseCaseScope
    fun provideDoneCount(
        @DatabaseRepositoryQualifier repository: TodoItemRepository
    ): GetDoneCountUseCase = GetDoneCountUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideItemsWithVisibility(
        @DatabaseRepositoryQualifier repository: TodoItemRepository
    ): GetItemsWithVisibilityUseCase = GetItemsWithVisibilityUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideGetById(
        @DatabaseRepositoryQualifier repository: TodoItemRepository
    ): GetTodoItemByIdOrNullUseCase = GetTodoItemByIdOrNullUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideFetch(
        @NetworkRepositoryQualifier netRepository: TodoItemRepository,
        @DatabaseRepositoryQualifier dbRepository: TodoItemRepository,
        tokenRepository: TokenRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): FetchItemsUseCase {
        return FetchItemsUseCase(
            netRepository, dbRepository, tokenRepository
        ) {
            snackbarShow(
                message = context.getString(R.string.fetch_error_message),
                snackbarHostState = snackbarHostState
            )
        }
    }

    @Provides
    @ItemsUseCaseScope
    fun provideDelete(
        @NetworkRepositoryQualifier netRepository: TodoItemRepository,
        @DatabaseRepositoryQualifier dbRepository: TodoItemRepository,
        tokenRepository: TokenRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): DeleteTodoItemUseCase {
        return DeleteTodoItemUseCase(netRepository, dbRepository, tokenRepository) {
            snackbarShow(
                message = context.getString(R.string.delete_error_message),
                actionLabel = "retry",
                snackbarHostState = snackbarHostState
            )
        }
    }

    @Provides
    @ItemsUseCaseScope
    fun provideUpdate(
        @NetworkRepositoryQualifier netRepository: TodoItemRepository,
        @DatabaseRepositoryQualifier dbRepository: TodoItemRepository,
        tokenRepository: TokenRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): UpdateTodoItemUseCase {
        return UpdateTodoItemUseCase(netRepository, dbRepository, tokenRepository) {
            snackbarShow(
                message = context.getString(R.string.update_error_message),
                actionLabel = "retry",
                snackbarHostState = snackbarHostState
            )
        }
    }

    @Provides
    @ItemsUseCaseScope
    fun provideAdd(
        @NetworkRepositoryQualifier netRepository: TodoItemRepository,
        @DatabaseRepositoryQualifier dbRepository: TodoItemRepository,
        tokenRepository: TokenRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): AddTodoItemUseCase {
        return AddTodoItemUseCase(netRepository, dbRepository, tokenRepository) {
            snackbarShow(
                message = context.getString(R.string.add_error_message),
                actionLabel = "retry",
                snackbarHostState = snackbarHostState
            )
        }
    }
}