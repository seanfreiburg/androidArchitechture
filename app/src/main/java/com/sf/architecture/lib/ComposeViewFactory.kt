package com.sf.architecture.lib

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.platform.ComposeView
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.bindShowRendering
import kotlin.reflect.KClass

@WorkflowUiExperimentalApi
@PublishedApi
internal class ComposeViewFactory<RenderingT : Any>(
    override val type: KClass<RenderingT>,
    internal val content: @Composable (RenderingT, ViewEnvironment) -> Unit
) : ViewFactory<RenderingT> {

    @OptIn(ExperimentalComposeApi::class)
    override fun buildView(
        initialRendering: RenderingT,
        initialViewEnvironment: ViewEnvironment,
        contextForNewView: Context,
        container: ViewGroup?
    ): View {
        val composeContainer = ComposeView(contextForNewView)

        // Update the state whenever a new rendering is emitted.
        // This lambda will be executed synchronously before bindShowRendering returns.
        composeContainer.bindShowRendering(
            initialRendering,
            initialViewEnvironment
        ) { rendering, environment ->
            // Entry point to the world of Compose.
            composeContainer.setContent {
                content(rendering, environment)
            }
        }

        return composeContainer
    }
}