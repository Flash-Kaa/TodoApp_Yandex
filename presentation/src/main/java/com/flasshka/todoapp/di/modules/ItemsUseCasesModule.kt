package com.flasshka.todoapp.di.modules

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import com.flasshka.domain.interfaces.TodoItemRepository
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
        repository: TodoItemRepository
    ): GetDoneCountUseCase = GetDoneCountUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideItemsWithVisibility(
        repository: TodoItemRepository
    ): GetItemsWithVisibilityUseCase = GetItemsWithVisibilityUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideGetById(
        repository: TodoItemRepository
    ): GetTodoItemByIdOrNullUseCase = GetTodoItemByIdOrNullUseCase(repository)

    @Provides
    @ItemsUseCaseScope
    fun provideFetch(
        repository: TodoItemRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): FetchItemsUseCase {
        return FetchItemsUseCase(repository) {
            snackbarShow(
                message = context.getString(R.string.fetch_error_message),
                actionLabel = "retry",
                snackbarHostState = snackbarHostState
            ) {
                repository.fetchItems()
            }
        }
    }

    @Provides
    @ItemsUseCaseScope
    fun provideDelete(
        repository: TodoItemRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): DeleteTodoItemUseCase {
        return DeleteTodoItemUseCase(repository) {
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
        repository: TodoItemRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): UpdateTodoItemUseCase {
        return UpdateTodoItemUseCase(repository) {
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
        repository: TodoItemRepository,
        context: Context,
        snackbarHostState: SnackbarHostState
    ): AddTodoItemUseCase {
        return AddTodoItemUseCase(repository) {
            snackbarShow(
                message = context.getString(R.string.add_error_message),
                actionLabel = "retry",
                snackbarHostState = snackbarHostState
            )
        }
    }
}