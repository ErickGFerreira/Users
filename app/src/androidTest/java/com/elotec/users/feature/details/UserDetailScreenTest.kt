package com.elotec.users.feature.details

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.elotec.users.domain.model.User
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var uiState: UserDetailUiState
    private val onActionEvent: (UserDetailScreenAction) -> Unit = {}

    @Before
    fun setup() {
        uiState = UserDetailUiState()
    }

    private fun setScreenWithUser(user: User) {
        composeTestRule.setContent {
            Screen(
                onActionEvent = onActionEvent,
                uiState = uiState
            )
        }
        uiState.showUserDetail(user)
    }

    @Test
    fun givenUserDetail_whenScreenDisplayed_thenShowTopAppBarText() {
        // When
        setScreenWithUser(User.mockUser())

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Detalhes do Usuário").assertIsDisplayed()
    }

    @Test
    fun givenUserDetail_whenScreenDisplayed_thenShowPersonalInfoTexts() {
        // When
        val user = User.mockUser()
        setScreenWithUser(user)

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Informações Pessoais").assertIsDisplayed()
        composeTestRule.onNodeWithText(user.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.username).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.email).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.phone).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.website).assertIsDisplayed()
    }

    @Test
    fun givenUserDetail_whenScreenDisplayed_thenShowAddressTexts() {
        // When
        val user = User.mockUser()
        setScreenWithUser(user)

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("address_info_card").performScrollTo()
        composeTestRule.onNodeWithText("Endereço").assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.street).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.suite).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.city).assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.zipcode).assertIsDisplayed()
    }

    @Test
    fun givenUserDetail_whenScreenDisplayed_thenShowGeoCoordinatesTexts() {
        // When
        val user = User.mockUser()
        setScreenWithUser(user)

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("address_info_card").performScrollTo()
        composeTestRule.onNodeWithText("Latitude").assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.geo.lat).assertIsDisplayed()
        composeTestRule.onNodeWithText("Longitude").assertIsDisplayed()
        composeTestRule.onNodeWithText(user.address.geo.lng).assertIsDisplayed()
    }
}
