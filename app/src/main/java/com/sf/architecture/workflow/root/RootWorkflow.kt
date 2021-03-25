package com.sf.architecture.workflow.root

import com.sf.architecture.workflow.hello.GetMessageWorker
import com.sf.architecture.workflow.hello.HelloWorkflow
import com.sf.architecture.workflow.hello.InvertMessageWorker
import com.sf.architecture.workflow.login.LoginWorkflow
import com.sf.architecture.workflow.root.RootWorkflow.*
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.Worker
import com.squareup.workflow1.WorkflowAction
import com.squareup.workflow1.runningWorker
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetAuthWorker : Worker<Boolean> {
    override fun run(): Flow<Boolean> = flowOf(false)
}

@OptIn(WorkflowUiExperimentalApi::class)
class RootWorkflow(
    private val viewModelScope: CoroutineScope,
    private val getAuthWorker: GetAuthWorker,
    private val getMessageWorker: GetMessageWorker,
    private val invertMessageWorker: InvertMessageWorker
) : StatefulWorkflow<Unit, State, Nothing, Any>() {

    sealed class State {
        object LoadingAuth : State()
        object Authenticating : State()
        object Authenticated : State()
    }

    sealed class Action : WorkflowAction<Unit, State, Nothing>() {
        class HandleAuthResponse(val loggedIn: Boolean) : Action()
        object OnLogout: Action()
        object OnLogin: Action()

        override fun Updater.apply() {
            state = reduce(this@Action)
        }

        companion object {
            fun reduce(action: Action): State {
                return when (action) {
                    is HandleAuthResponse -> {
                        if (action.loggedIn) {
                            State.Authenticated
                        } else {
                            State.Authenticating
                        }
                    }
                    OnLogout -> State.Authenticating
                    OnLogin -> State.Authenticated
                }
            }
        }
    }

    override fun initialState(
        props: Unit,
        snapshot: Snapshot?
    ): State = State.LoadingAuth

    override fun render(
        renderProps: Unit,
        renderState: State,
        context: RenderContext
    ): Any {
        when (renderState) {
            State.LoadingAuth -> {
                context.runningWorker(getAuthWorker, "auth") {
                    Action.HandleAuthResponse(it)
                }
                return Unit
            }
            State.Authenticated -> {

                // this is being recreated on every action lol
                val childWorkflow = HelloWorkflow(
                    getMessageWorker,
                    invertMessageWorker
                )
                return context.renderChild(childWorkflow, props = Unit) {
                    Action.OnLogout
                }
            }
            State.Authenticating -> {
                val childWorkflow = LoginWorkflow()
                return context.renderChild(childWorkflow, props = Unit) {
                    Action.OnLogin
                }
            }
        }


    }

    // It'd be silly to restore an in progress login session, so saves nothing.
    override fun snapshotState(state: State): Snapshot? = null
}

