package com.sf.architecture.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sf.architecture.app.ViewEvent.LoginViewEvent
import com.sf.architecture.app.ViewModel.LoginViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val navigator: Navigator): MoleculePresenter<LoginViewEvent, LoginViewModel> {
    @Composable
    override fun viewModel(events: Flow<LoginViewEvent>): LoginViewModel {

        LaunchedEffect(key1 = "events") {
            events.collect {
                when(it){
                    LoginViewEvent.OnBackClick -> navigator.goBack()
                }
            }
        }

        return LoginViewModel.Loading
    }
}