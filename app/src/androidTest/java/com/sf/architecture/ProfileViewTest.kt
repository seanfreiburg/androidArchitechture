package com.sf.architecture

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.sf.architecture.profile.ProfileView
import com.sf.architecture.profile.ProfileViewEvents
import com.sf.architecture.profile.ProfileViewModel
import com.sf.architecture.app.RootActivity
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test


class ProfileViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<RootActivity>()
    // createComposeRule() if you don't need access to the activityTestRule

    @Test
    fun messageRenders() {
        // GIVEN
        composeTestRule.setContent {
            com.sf.architecture.profile.ProfileView(
                MutableStateFlow(com.sf.architecture.profile.ProfileViewEvents.Init),
                MutableStateFlow(com.sf.architecture.profile.ProfileViewModel.Loading)
            )
        }

        // THEN
        composeTestRule.onNodeWithText("Loading…").assertIsDisplayed()
    }
}