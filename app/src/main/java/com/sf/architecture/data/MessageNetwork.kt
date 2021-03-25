package com.sf.architecture.data

import com.sf.architecture.domain.MessageState
import com.sf.architecture.shared.Response

interface MessageNetwork {
    suspend fun fetchMessage(): Response<MessageState>
    suspend fun saveMessage(messageState: MessageState): Response<Unit>
}

class FakeMessageNetwork: MessageNetwork {
    private var message: String = "Hello"

    override suspend fun fetchMessage(): Response<MessageState> {
        return Response.Success(MessageState.valueOf(message))
    }

    override suspend fun saveMessage(messageState: MessageState): Response<Unit> {
        message = messageState.name
        return Response.Success(Unit)
    }
}