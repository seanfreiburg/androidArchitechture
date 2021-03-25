package com.sf.architecture.workflow

import com.sf.architecture.domain.MessageState
import com.sf.architecture.ui.HelloScreen
import com.sf.architecture.workflow.HelloWorkflow.State
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.WorkflowAction
import com.squareup.workflow1.runningWorker

class HelloWorkflow(
    private val getMessageWorker: GetMessageWorker,
    private val invertMessageWorker: InvertMessageWorker
) : StatefulWorkflow<Unit, State, Nothing, HelloScreen>() {

    sealed class State() {
        object Loading : State()
        data class Loaded(val messageState: MessageState) : State()
        object Inverting : State()
    }

    sealed class Action : WorkflowAction<Unit, State, Nothing>() {
        object OnClick : Action()
        class OnMessageUpdate(val messageState: MessageState): Action()

        override fun Updater.apply() {
            state = reduce(this@Action)
        }

        companion object {
            fun reduce(action: Action): State {
                return when (action) {
                    OnClick -> State.Inverting
                    is OnMessageUpdate -> State.Loaded(action.messageState)
                }
            }
        }
    }

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = State.Loading

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): HelloScreen {

        // always listen for updates
        context.runningWorker(getMessageWorker, "message") {
            Action.OnMessageUpdate(it)
        }

        val onClick = { context.actionSink.send(Action.OnClick) }

        when (renderState) {
            State.Loading -> {
                return HelloScreen(
                    message = "Loading...",
                    onClick = onClick
                )
            }
            is State.Loaded -> {
                return HelloScreen(
                    message = renderState.messageState.name,
                    onClick = onClick
                )
            }
            is State.Inverting -> {
                context.runningWorker(invertMessageWorker, "invert") {
                    Action.OnMessageUpdate(it)
                }

                return HelloScreen(
                    message = "Inverting...",
                    onClick = onClick
                )
            }
        }
    }

    override fun snapshotState(state: State): Snapshot? = null
}

