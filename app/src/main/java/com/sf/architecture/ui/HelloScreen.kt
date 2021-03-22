package com.sf.architecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sf.architecture.ui.theme.ArchitectureTheme
import com.squareup.workflow1.ui.AndroidViewRendering
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi


@OptIn(WorkflowUiExperimentalApi::class)
data class HelloScreen(val message: String,
                  val onClick: () -> Unit): AndroidViewRendering<HelloScreen> {
    override val viewFactory: ViewFactory<HelloScreen> = ComposeViewFactory(
        type = HelloScreen::class,
        content = { helloScreen: HelloScreen, viewEnvironment: ViewEnvironment ->
            ComposeHomeScreen(helloScreen.message, helloScreen.onClick)
        }
    )
}

@Composable
fun ComposeHomeScreen(
    message: String,
    onClick: () -> Unit) {
    ArchitectureTheme {
        Column {
            Row {
                Text(text = message)
            }
            Row {
                Button(onClick = onClick) {
                    Text(text = "click me")
                }
            }
        }
    }
}