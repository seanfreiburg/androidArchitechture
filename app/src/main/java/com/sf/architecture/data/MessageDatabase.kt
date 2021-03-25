package com.sf.architecture.data

import com.sf.architecture.domain.MessageState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface MessageDatabase {
    val message: MessageState?
    val messageFlow: Flow<MessageState?>
    suspend fun setMessage(state: MessageState)
}

class FakeMessageDatabase: MessageDatabase {
    private val messageStateFlow: MutableStateFlow<MessageState?> = MutableStateFlow(null)

    override val message: MessageState? = messageStateFlow.value
    override val messageFlow: Flow<MessageState?> = messageStateFlow
    override suspend fun setMessage(state: MessageState) {
        messageStateFlow.value = state
    }
}

