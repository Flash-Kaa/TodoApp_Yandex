package com.flasshka.todoapp.ui.info.divkit

import com.flasshka.todoapp.actions.InfoActionType
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class ActionHandler(
    private val getAction: (InfoActionType) -> (() -> Unit)
): DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        val url = action.url?.evaluate(resolver) ?: return super.handleAction(action, view, resolver)

        if (url.scheme == SCHEME_INFO) {
            getAction(InfoActionType.Close).invoke()
        }

        return super.handleAction(action, view, resolver)
    }

    companion object {
        const val SCHEME_INFO = "info-div-action"
    }
}