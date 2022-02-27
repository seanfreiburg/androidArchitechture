package com.sf.architecture.app

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sf.architecture.app.ViewEvent.LoginViewEvent
import com.sf.architecture.app.ViewEvent.LoginViewEvent.OnBackClick
import com.sf.architecture.app.ViewModel.LoginViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class LoginView: MoleculeView<LoginViewEvent, LoginViewModel> {

    private lateinit var events: MutableSharedFlow<LoginViewEvent>
    override fun setEventReceiver(events: MutableSharedFlow<LoginViewEvent>) {
        this.events = events
    }

    @Composable
    override fun Render(viewModel: LoginViewModel) {
        Text(text = "Login")
        Button(onClick = {
            val success = events.tryEmit(OnBackClick)
            val b = success
        }) {
            Text(text = "Go Back")
        }
    }

}