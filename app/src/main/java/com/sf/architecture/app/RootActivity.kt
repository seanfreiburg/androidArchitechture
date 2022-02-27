package com.sf.architecture.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.launchMolecule
import com.sf.architecture.app.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RootActivity : ComponentActivity() {
    private val scope = CoroutineScope(AndroidUiDispatcher.Main)

    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RootApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        val navigator = RealNavigator(SplashScreen)
        scope.launch {
            navigator.screensFlow.collect {
                val events = MutableSharedFlow<ViewEvent>(0, 50)
                val view = it.first as MoleculeView<ViewEvent, ViewModel>
                val presenter = it.second as MoleculePresenter<ViewEvent, ViewModel>

                view.setEventReceiver(events)

                launch {
                    val models = launchMolecule {
                        presenter.viewModel(events = events)
                    }
                    models.collect { model ->
                        setContent {
                            AppTheme {
                                // A surface container using the 'background' color from the theme
                                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                                    view.Render(model)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}

