package com.sf.architecture.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.sf.architecture.lib.ComposeViewFactory
import com.sf.architecture.ui.theme.ArchitectureTheme
import com.squareup.workflow1.ui.AndroidViewRendering
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewFactory
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi

@OptIn(WorkflowUiExperimentalApi::class)
data class LoginScreen(val onClick: () -> Unit): AndroidViewRendering<LoginScreen> {
    override val viewFactory: ViewFactory<LoginScreen> = ComposeViewFactory(
        type = LoginScreen::class,
        content = { loginScreen: LoginScreen, viewEnvironment: ViewEnvironment ->
            ComposeLoginScreen(loginScreen.onClick)
        }
    )
}

@Composable
fun ComposeLoginScreen(
    onClick: () -> Unit) {
    ArchitectureTheme {
        Column {
            Row {
                Button(onClick = onClick) {
                    Text(text = "login")
                }
            }
        }
    }
}