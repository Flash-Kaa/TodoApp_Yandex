package com.flasshka.domain.usecases

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

suspend fun runWithSupervisorInBackground(
    tryCount: UByte = 1u,
    onErrorAction: (suspend () -> Unit)? = null,

    content: suspend CoroutineScope.() -> Unit
) {
    if (tryCount.compareTo(0u) == 0) return

    supervisorScope {
        launch(
            context = Dispatchers.IO + CoroutineExceptionHandler { _, e ->
                runAfterException(onErrorAction, tryCount, content)
            },
            block = content
        )
    }
}

private fun CoroutineScope.runAfterException(
    onErrorAction: (suspend () -> Unit)?,
    tryCount: UByte,
    content: suspend CoroutineScope.() -> Unit
) {
    launch(Dispatchers.Main.immediate) {
        onErrorAction?.invoke()
    }

    launch {
        delay(8000L)
        runWithSupervisorInBackground(tryCount.dec(), onErrorAction, content)
    }
}