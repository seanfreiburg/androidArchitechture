package com.sf.architecture.repo

import com.sf.architecture.domain.MessageRepo
import com.sf.architecture.domain.MessageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeMessageRepo : MessageRepo {
    private val messageStateFlow = MutableStateFlow(MessageState.Goodbye)
    override val message: StateFlow<MessageState> = messageStateFlow

    override suspend fun setMessage(state: MessageState) {
        messageStateFlow.value = state
    }
}