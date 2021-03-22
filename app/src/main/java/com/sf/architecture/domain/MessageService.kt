package com.sf.architecture.domain

import com.sf.architecture.workflow.HelloWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.asWorker
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface MessageService {
    val message: Flow<HelloWorkflow.State>
}

class RealMessageService : MessageService {
    val messageStateFlow = MutableStateFlow(HelloWorkflow.State.Goodbye)
    override val message: Flow<HelloWorkflow.State> = messageStateFlow

    init {
        GlobalScope.launch {
            while (isActive){
                messageStateFlow.value = if (messageStateFlow.value == HelloWorkflow.State.Hello) HelloWorkflow.State.Goodbye else HelloWorkflow.State.Hello
                delay(1000)
            }

        }
    }
}

class FakeMessageService : MessageService {
    override val message: Flow<HelloWorkflow.State>
        get() = TODO("Not yet implemented")

}
