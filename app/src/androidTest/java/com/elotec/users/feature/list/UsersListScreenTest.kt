package com.elotec.users.feature.list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.elotec.users.domain.model.User
import com.elotec.users.utils.error.Error
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UsersListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var uiState: UsersListUiState
    private val onActionEvent: (UsersListScreenAction) -> Unit = {}

    @Before
    fun setup() {
        uiState = UsersListUiState()
    }

    private fun setScreen() {
        composeTestRule.setContent {
            Screen(
                onActionEvent = onActionEvent,
                uiState = uiState
            )
        }
    }

    @Test
    fun givenLoadingState_whenScreenIsDisplayed_thenShowProgressIndicator() {
        // When
        setScreen()
        uiState.showProgress()

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("progress_indicator").assertExists()
    }

    @Test
    fun givenErrorState_whenScreenIsDisplayed_thenShowErrorTexts() {
        // When
        setScreen()
        uiState.showToastError(
            error = Error(
                title = "Erro ao carregar",
                message = "Tente novamente"
            )
        )

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Erro ao carregar").assertIsDisplayed()
    }

    @Test
    fun givenUsersList_whenScreenIsDisplayed_thenShowUsersTexts() {
        // When
        val users = User.mockList()
        setScreen()
        uiState.showScreen(userList = users)

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(users.first().name).assertIsDisplayed()
        composeTestRule.onNodeWithText(users.first().email).assertIsDisplayed()
    }

    @Test
    fun givenPaginationError_whenErrorOccurs_thenShowToastText() {
        // When
        setScreen()
        uiState.showScreen(userList = User.mockList())
        uiState.showToastError(
            error = Error(
                title = "Erro ao carregar mais",
                message = "Tente novamente"
            )
        )

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Erro ao carregar mais").assertIsDisplayed()
    }

    @Test
    fun givenUsersList_whenSearchPerformed_thenShowFilteredResults() {
        // When
        val users = User.mockList()
        setScreen()
        uiState.showScreen(userList = users)
        composeTestRule.waitForIdle()
        
        // Digitar no campo de busca
        composeTestRule.onNodeWithTag("search_field").performTextInput("Erick")

        // Then
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Erick").assertIsDisplayed()
        composeTestRule.onNodeWithText("erick@gmail.com").assertIsDisplayed()
    }
}

