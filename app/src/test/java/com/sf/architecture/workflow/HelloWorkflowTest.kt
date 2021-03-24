package com.sf.architecture.workflow

import com.google.common.truth.Truth.assertThat
import com.sf.architecture.domain.FakeMessageUseCase
import com.sf.architecture.domain.MessageState
import com.sf.architecture.workflow.HelloWorkflow.State
import com.sf.architecture.workflow.HelloWorkflow.State.Loaded
import com.squareup.workflow1.testing.expectWorker
import com.squareup.workflow1.testing.testRender
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class HelloWorkflowTest {

    private val fakeMessageUseCase = FakeMessageUseCase()
    private val testObject = HelloWorkflow(
        GetMessageWorker(fakeMessageUseCase),
        InvertMessageWorker(fakeMessageUseCase)
    )


    // snapshot
    @Test
    fun `testInitialState when snapshot is Loading`() {
        val initialState = testObject.initialState(
            Unit,
            null
        )

        assertThat(initialState).isEqualTo(State.Loading)
    }

    @Test
    fun testSnapshotStateNull() {
        val snapshot = testObject.snapshotState(Loaded(MessageState.Hello))

        assertThat(snapshot).isEqualTo(null)
    }

    // test render
    @Test
    fun `testRender when Loading then message and worker are correct`() {
        val testRender = testObject.testRender(Unit, State.Loading)

        testRender.render {
            assertThat(it.message).isEqualTo("Loading...")
        }

        testRender.expectWorker(
            workerClass = GetMessageWorker::class,
            key = "message"
        )
    }

    @Test
    fun `testRender when in state Inverting then message and worker are correct`() {
        val testRender = testObject.testRender(Unit, State.Inverting)

        testRender.render {
            assertThat(it.message).isEqualTo("Inverting...")
        }

        testRender.expectWorker(
            workerClass = InvertMessageWorker::class,
            key = "invert"
        )
    }

    @Test
    fun `testRender when in state Loaded then message and worker are correct`() {
        val testRender = testObject.testRender(Unit, Loaded(MessageState.Hello))

        testRender.render {
            assertThat(it.message).isEqualTo("Hello")
        }

        testRender.expectWorker(
            workerClass = GetMessageWorker::class,
            key = "message"
        )
    }

    // actions

    @Test
    fun `action when onClick then state to Inverting`() {
        val testRender = testObject.testRender(Unit, Loaded(MessageState.Hello))
        val render = testRender.render { rendering ->
            rendering.onClick()
        }

        render.verifyAction { action ->
            assertThat(action).isEqualTo(HelloWorkflow.Action.OnClick)
        }
        render.verifyActionResult { newState, output ->
            assertThat(newState).isEqualTo(State.Inverting)
            assertThat(output).isNull()
        }
    }

    // reducer
    @Test
    fun `reducer when OnMessageUpdate then state to Loaded`() {
        val newState = HelloWorkflow.Action.reduce(HelloWorkflow.Action.OnMessageUpdate(MessageState.Hello))
        assertThat(newState).isEqualTo(Loaded(MessageState.Hello))
    }

    @Test
    fun `reducer when OnClick then state to Inverting`() {
        val newState = HelloWorkflow.Action.reduce(HelloWorkflow.Action.OnClick)
        assertThat(newState).isEqualTo(State.Inverting)
    }
}