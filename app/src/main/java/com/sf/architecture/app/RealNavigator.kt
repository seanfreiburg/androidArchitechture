package com.sf.architecture.app

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class RealNavigator(rootScreen: Screen): Navigator {
    private val stack = mutableListOf<Pair<Any, Any>>()

    private val mutableScreensFlow: MutableStateFlow<Pair<Any, Any>> = MutableStateFlow(
        buildConnectedScreen(rootScreen).also {
            stack.add(it)
        }
    )

    override val screensFlow: Flow<Pair<Any, Any>> = mutableScreensFlow

    override fun goToScreen(screen: Screen) {
        val connectedScreen = buildConnectedScreen(screen)
        stack.add(connectedScreen)
        mutableScreensFlow.value = stack.last()
    }

    private fun buildConnectedScreen(screen: Screen): Pair<Any, Any> {
        val view = when (screen) {
            SplashScreen -> SplashView(this)
            LoginScreen -> LoginView()
        }
        val presenter = when (screen) {
            SplashScreen -> SplashPresenter()
            LoginScreen -> LoginPresenter(this)
        }

        return Pair(view, presenter)
    }

    override fun goBack() {
        require(stack.isNotEmpty())
        stack.removeLast()
        mutableScreensFlow.value = stack.last()
    }
}