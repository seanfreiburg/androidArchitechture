package com.sf.architecture.app

sealed class ViewEvent {
    sealed class SplashViewEvent: ViewEvent() {

    }

    sealed class LoginViewEvent: ViewEvent() {
        object OnBackClick: LoginViewEvent()
    }
}
