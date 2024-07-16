package com.flasshka.todoapp.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.flasshka.domain.entities.Settings
import com.flasshka.domain.usecases.settings.GetSettingsUseCase
import com.flasshka.domain.usecases.settings.UpdateSettingsUseCase
import com.flasshka.todoapp.actions.SettingsActionType
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.settings.SettingsStateUI.Companion.toStateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsVM(
    private val router: Router,
    private val getSettings: GetSettingsUseCase,
    private val updateSettings: UpdateSettingsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<SettingsStateUI> = MutableStateFlow(SettingsStateUI())
    val state = _state.asStateFlow()

    private var unchanged: SettingsStateUI by mutableStateOf(SettingsStateUI())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSettings().collect {
                val newState = it.toStateUI()
                _state.update { newState }

                withContext(Dispatchers.Main) {
                    unchanged = newState
                }
            }
        }
    }

    fun getAction(action: SettingsActionType): () -> Unit {
        return when (action) {
            is SettingsActionType.OnExit -> ::onExit
            is SettingsActionType.OnSave -> ::onSave
            is SettingsActionType.GoToInfo -> ::goToInfo
            is SettingsActionType.OnChangeAppTheme -> onChangeTheme(action.newTheme)
        }
    }

    fun saveIsEnabled(): Boolean {
        return unchanged != state.value
    }

    private fun onExit() {
        router.navigateToListOfItems()
    }

    private fun onSave() {
        unchanged = state.value

        viewModelScope.launch {
            updateSettings(state.value.toSettings())
        }
    }

    private fun goToInfo() {
        router.navigateToInfo()
    }

    private fun onChangeTheme(theme: Settings.AppTheme): () -> Unit {
        return {
            updateTheme(theme)
        }
    }

    private fun updateTheme(theme: Settings.AppTheme) {
        _state.update {
            it.copy(
                theme = theme
            )
        }
    }

    /**
     * Creates a SettingsVM without @AssistedInject
     */
    class FactoryWrapperWithUseCases(
        private val getSettingsUseCase: GetSettingsUseCase,
        private val updateSettingsUseCase: UpdateSettingsUseCase
    ) {
        inner class InnerFactory(
            private val router: Router
        ) : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsVM(
                    router = router,
                    getSettings = getSettingsUseCase,
                    updateSettings = updateSettingsUseCase
                ) as T
            }
        }
    }
}