package com.sf.architecture.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

enum class MessageState {
    Hello,
    Goodbye
}

interface MessageUseCase {
    val message: StateFlow<MessageState>
    fun invertMessage(): Flow<MessageState>
}

class RealMessageUseCase(private val messageRepo: MessageRepo): MessageUseCase {

    override val message: StateFlow<MessageState> = messageRepo.message
    override fun invertMessage(): Flow<MessageState> = flow {
        val newState = when (message.value) {
            MessageState.Hello -> MessageState.Goodbye
            MessageState.Goodbye -> MessageState.Hello
        }
        messageRepo.setMessage(newState)
        emit(newState)
    }
}

class FakeMessageUseCase : MessageUseCase {
    private val messageStateFlow = MutableStateFlow(MessageState.Goodbye)
    override val message: StateFlow<MessageState> = messageStateFlow
    override fun invertMessage(): Flow<MessageState> = flow {
        val newState = when (messageStateFlow.value) {
            MessageState.Hello -> MessageState.Goodbye
            MessageState.Goodbye -> MessageState.Hello
        }
        messageStateFlow.value = newState
        emit(newState)
    }
}
