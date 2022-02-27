package com.sf.architecture.app

import androidx.compose.runtime.Composable
import com.sf.architecture.app.ViewEvent.SplashViewEvent
import com.sf.architecture.app.ViewModel.SplashViewModel
import kotlinx.coroutines.flow.Flow

class SplashPresenter: MoleculePresenter<SplashViewEvent, SplashViewModel> {

    @Composable
    override fun viewModel(events: Flow<SplashViewEvent>): SplashViewModel {
        return SplashViewModel.Loading
    }
}

