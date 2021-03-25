package com.sf.architecture.workflow.login

import com.sf.architecture.ui.LoginScreen
import com.sf.architecture.workflow.login.LoginWorkflow.*
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.WorkflowAction

class LoginWorkflow : StatefulWorkflow<Unit, State, LoginResult, LoginScreen>() {

    sealed class State {
        object Authorizing : State()
    }

    sealed class LoginResult {
        object Authorized : LoginResult()
        object Canceled : LoginResult()
    }

    sealed class Action : WorkflowAction<Unit, State, LoginResult>() {
        object OnLoginClick : Action()

        override fun Updater.apply() {
            when (this@Action) {
                OnLoginClick -> setOutput(LoginResult.Authorized)
            }
        }
    }

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = State.Authorizing

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): LoginScreen {

        val onClick = { context.actionSink.send(Action.OnLoginClick) }

        when (renderState) {
            State.Authorizing -> {
                return LoginScreen(
                    onClick = onClick
                )
            }
        }
    }

    override fun snapshotState(state: State): Snapshot? = null
}

