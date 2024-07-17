package com.flasshka.todoapp.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flasshka.todoapp.actions.InfoActionType
import com.flasshka.todoapp.navigation.Router

class InfoVM(
    private val router: Router
) : ViewModel() {

    fun getAction(action: InfoActionType): () -> Unit {
        return when (action) {
            is InfoActionType.Close -> ::close
        }
    }

    private fun close() {
        router.back()
    }

    /**
     * Creates a InfoVM
     */
    class InnerFactory(
        private val router: Router
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return InfoVM(
                router = router,
            ) as T
        }
    }
}