package com.sf.architecture.workflow

import com.sf.architecture.ui.HelloScreen
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.parse

object HelloWorkflow : StatefulWorkflow<Unit, HelloWorkflow.State, Nothing, HelloScreen>() {
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