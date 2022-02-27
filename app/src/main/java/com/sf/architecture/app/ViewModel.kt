package com.sf.architecture.app

sealed class ViewModel {
    sealed class SplashViewModel : ViewModel() {
        object Loading: SplashViewModel()
    }

    sealed class LoginViewModel : ViewModel() {
        object Loading: LoginViewModel()
    }
}
