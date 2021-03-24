package com.sf.architecture.domain

import kotlinx.coroutines.flow.StateFlow


interface MessageRepo {
    val message: StateFlow<MessageState>
    suspend fun setMessage(state: MessageState)
}

