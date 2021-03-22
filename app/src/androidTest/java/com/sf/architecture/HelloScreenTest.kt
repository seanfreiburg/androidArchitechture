package com.sf.architecture

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth.assertThat
import com.sf.architecture.app.HelloWorkflowActivity
import com.sf.architecture.ui.ComposeHomeScreen
import org.junit.Rule
import org.junit.Test


class HelloScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<HelloWorkflowActivity>()
    // createComposeRule() if you don't need access to the activityTestRule

    @Test
    fun messageRenders() {
        // GIVEN
        composeTestRule.setContent {
            ComposeHomeScreen("hello", onClick = {})
        }

        // THEN
        composeTestRule.onNodeWithText("hello").assertIsDisplayed()
    }

    @Test
    fun clickCalled() {
        // GIVEN
        var clicked = false
        composeTestRule.setContent {
            ComposeHomeScreen("hello", onClick = {
                clicked = true
            })
        }

        // WHEN
        composeTestRule.onNodeWithText("click me").performClick()

        // THEN
        assertThat(clicked).isTrue()
    }
}