package com.sf.architecture.repo

import com.sf.architecture.data.MessageDatabase
import com.sf.architecture.data.MessageNetwork
import com.sf.architecture.domain.MessageRepo
import com.sf.architecture.domain.MessageState
import com.sf.architecture.shared.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RealMessageRepo(
    private val coroutineScope: CoroutineScope,
    private val messageNetwork: MessageNetwork,
    private val messageDatabase: MessageDatabase
) : MessageRepo {

    private val messageStateFlow = MutableStateFlow(MessageState.Goodbye)
    override val message: StateFlow<MessageState> = messageStateFlow

    init {
        coroutineScope.launch {
            val databaseMessageState = messageDatabase.message
            if (databaseMessageState == null) {
                val networkResponse = messageNetwork.fetchMessage()
                when (networkResponse) {
                    is Response.Success -> messageDatabase.setMessage(networkResponse.value)
                    else -> TODO("Print an error or something")
                }
            }
        }

        messageDatabase.messageFlow.onEach {
            if (it != null){
                messageStateFlow.value = it
            }
        }.launchIn(coroutineScope)
    }

    override suspend fun setMessage(state: MessageState) {
        messageNetwork.saveMessage(state)
        messageDatabase.setMessage(state)
        messageStateFlow.value = state
    }

}