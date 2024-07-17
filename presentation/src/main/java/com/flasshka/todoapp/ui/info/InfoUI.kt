package com.flasshka.todoapp.ui.info

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.flasshka.todoapp.actions.InfoActionType
import com.flasshka.todoapp.navigation.Router
import com.flasshka.todoapp.ui.info.divkit.ActionHandler
import com.flasshka.todoapp.ui.info.divkit.AssetReader
import com.yandex.div.DivDataTag
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.core.view2.Div2View
import com.yandex.div.data.DivParsingEnvironment
import com.yandex.div.json.ParsingErrorLogger
import com.yandex.div.picasso.PicassoDivImageLoader
import com.yandex.div2.DivData
import org.json.JSONObject

@Composable
fun InfoUI(
    snackbarHostState: SnackbarHostState,
    getAction: (InfoActionType) -> (() -> Unit)
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { DivKitView(getAction, Modifier.padding(it)) }
    )
}

@Composable
fun DivKitView(
    getAction: (InfoActionType) -> (() -> Unit),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val divData = AssetReader(context).read("ui.json").asDiv2DataWithTemplates()

    val div2Context = Div2Context(
        baseContext = ContextThemeWrapper(context, 0),
        configuration = createDivConfiguration(context, getAction),
        lifecycleOwner = LocalLifecycleOwner.current
    )

    AndroidView(
        modifier = modifier,
        factory = { Div2View(div2Context) },
        update = { view -> view.setData(divData, DivDataTag("div2")) },
        onRelease = { view -> view.cleanup() }
    )
}

private fun JSONObject.asDiv2DataWithTemplates(): DivData {
    val templates = getJSONObject("templates")
    val card = getJSONObject("card")

    val environment = DivParsingEnvironment(ParsingErrorLogger.LOG)
        .apply { parseTemplates(templates) }

    return DivData(environment, card)
}

private fun createDivConfiguration(
    context: Context,
    getAction: (InfoActionType) -> (() -> Unit)
): DivConfiguration {
    return DivConfiguration
        .Builder(PicassoDivImageLoader(context))
        .actionHandler(ActionHandler(getAction))
        .build()
}