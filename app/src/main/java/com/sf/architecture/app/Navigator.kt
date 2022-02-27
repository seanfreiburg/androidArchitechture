package com.sf.architecture.app

import kotlinx.coroutines.flow.Flow

interface Navigator {
    val screensFlow: Flow<Pair<Any, Any>>
    fun goToScreen(screen: Screen)
    fun goBack()
}