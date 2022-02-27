package com.sf.architecture.app

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.sf.architecture.app.ViewEvent.SplashViewEvent
import com.sf.architecture.app.ViewModel.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

class SplashView(private val navigator: Navigator) :
    MoleculeView<SplashViewEvent, SplashViewModel> {

    override fun setEventReceiver(events: MutableSharedFlow<SplashViewEvent>) {
        // TODO("Not yet implemented")
    }

    @Composable
    override fun Render(viewModel: SplashViewModel) {
        val doneLoading = remember {
            mutableStateOf(false)
        }

        val timesLoaded = remember {
            mutableStateOf(0)
        }

        timesLoaded.value = timesLoaded.value + 1
        LaunchedEffect(key1 = "key") {
            delay(3000)
            navigator.goToScreen(LoginScreen)
        }
        Text(text = "SplashScreen timesLoaded: ${timesLoaded.value}")
    }

}

