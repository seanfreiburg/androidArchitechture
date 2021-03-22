package com.sf.architecture.workflow

import com.sf.architecture.domain.MessageService
import com.sf.architecture.ui.HelloScreen
import com.sf.architecture.workflow.HelloWorkflow.*
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.WorkflowAction.Companion.emitOutput
import com.squareup.workflow1.action
import com.squareup.workflow1.onWorkerOutput
import com.squareup.workflow1.parse
import com.squareup.workflow1.runningWorker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class HelloWorkflow(private val messageWorker: Worker<State>) : StatefulWorkflow<Unit, State, Nothing, HelloScreen>() {
    enum class State {
        Hello,
        Goodbye
    }

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = snapshot?.bytes?.parse { source -> if (source.readInt() == 1) State.Hello else State.Goodbye }
        ?: State.Hello

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): HelloScreen {

        context.runningWorker(messageWorker) {
                time -> helloAction
        }

        return HelloScreen(
            message = renderState.name,
            onClick = { context.actionSink.send(helloAction) }
        )
    }

    override fun snapshotState(state: State): Snapshot =
        Snapshot.of(if (state == State.Hello) 1 else 0)

    private val helloAction = action {
        state = when (state) {
            State.Hello -> State.Goodbye
            State.Goodbye -> State.Hello
        }
    }
}