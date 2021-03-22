package com.sf.architecture.workflow

import com.google.common.truth.Truth.assertThat
import com.sf.architecture.workflow.HelloWorkflow.State.*
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.testing.testRender
import org.junit.Before
import org.junit.Test


class HelloWorkflowTest {

    @Test
    fun `testInitialState when snapshot is null`() {
        val initialState = HelloWorkflow.initialState(
            Unit,
            null
        )

        assertThat(initialState).isEqualTo(Hello)
    }

    @Test
    fun `testInitialState when snapshot is Hello`() {
        val initialState = HelloWorkflow.initialState(
            Unit,
            Snapshot.Companion.of(1)
        )

        assertThat(initialState).isEqualTo(Hello)
    }

    @Test
    fun `testInitialState when snapshot is Goodbye`() {
        val initialState = HelloWorkflow.initialState(
            Unit,
            Snapshot.Companion.of(0)
        )

        assertThat(initialState).isEqualTo(Goodbye)
    }

    @Test
    fun `testRender when onClick then state Hello to Goodbye`() {
        val testRender = HelloWorkflow.testRender(Unit, Hello)
        testRender.render { rendering ->
            rendering.onClick()
        }.verifyActionResult { newState, output ->
            assertThat(newState).isEqualTo(Goodbye)
            assertThat(output).isNull()
        }
    }

    @Test
    fun `testRender when onClick then state Goodbye to Hello`() {
        val testRender = HelloWorkflow.testRender(Unit, Goodbye)
        testRender.render { rendering ->
            rendering.onClick()
        }.verifyActionResult { newState, output ->
            assertThat(newState).isEqualTo(Hello)
            assertThat(output).isNull()
        }
    }

    @Test
    fun testSnapshotStateHello() {
        val snapshot = HelloWorkflow.snapshotState(Hello)

        assertThat(snapshot).isEqualTo(Snapshot.of(1))
    }

    @Test
    fun testSnapshotStateGoodbye() {
        val snapshot = HelloWorkflow.snapshotState(Goodbye)

        assertThat(snapshot).isEqualTo(Snapshot.of(0))
    }
}