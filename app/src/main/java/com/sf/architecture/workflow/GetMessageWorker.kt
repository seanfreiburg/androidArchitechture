package com.sf.architecture.workflow

import com.sf.architecture.domain.MessageState
import com.sf.architecture.domain.MessageUseCase
import com.squareup.workflow1.Worker
import kotlinx.coroutines.flow.Flow

class GetMessageWorker(private val messageUseCase: MessageUseCase): Worker<MessageState> {
    override fun run(): Flow<MessageState> = messageUseCase.message
}