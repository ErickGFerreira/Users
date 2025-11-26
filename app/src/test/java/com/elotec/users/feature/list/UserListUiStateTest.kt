package com.elotec.users.feature.list

import com.elotec.users.common.ScreenState
import com.elotec.users.domain.model.User
import com.elotec.users.utils.error.Error
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class UsersListUiStateTest {
    private lateinit var uiState: UsersListUiState

    @Before
    fun setup() {
        uiState = UsersListUiState()
    }

    @Test
    fun `given user list, when showScreen, then show content with correct data`() {
        // given
        uiState.showScreen(
            userList = User.mockList()
        )
        // then
        assertThat(uiState.screenPresentation.value).isEqualTo(DEFAULT_PRESENTATION)
        assertThat(uiState.filteredScreenPresentation.value).isEqualTo(DEFAULT_PRESENTATION)
        assertThat(uiState.screenState.value).isEqualTo(ScreenState.ScreenContent)
    }

    @Test
    fun `given user list, when appendUsers, then show content with appendedData`() {
        // given
        uiState.screenPresentation.value = DEFAULT_PRESENTATION
        uiState.showScreen(
            userList = User.mockList()
        )
        // then
        assertThat(uiState.screenPresentation.value).isEqualTo(
            DEFAULT_PRESENTATION.copy(
                users = appendList(DEFAULT_PRESENTATION.users, User.mockList())
            )
        )
        assertThat(uiState.filteredScreenPresentation.value).isEqualTo(
            DEFAULT_PRESENTATION.copy(
                users = appendList(DEFAULT_PRESENTATION.users, User.mockList())
            )
        )
        assertThat(uiState.screenState.value).isEqualTo(ScreenState.ScreenContent)
    }

    @Test
    fun `given screenState, when showProgress, then switch screen state to ScreenProgress`() {
        // given
        uiState.showProgress()
        // then
        assertThat(uiState.screenState.value).isEqualTo(ScreenState.ScreenProgress)
    }

    @Test
    fun `given ERROR_MOCK, when showError, then switch screen state to ScreenError and show error`() {
        // given
        uiState.showError(error = ERROR_MOCK)
        // then
        assertThat(uiState.screenState.value).isEqualTo(ScreenState.ScreenError(error = ERROR_MOCK))
    }

    @Test
    fun `given name, when search, then show screen with filteredData`() {
        // given
        uiState.showScreen(userList = DEFAULT_PRESENTATION.users)
        uiState.search(queryText = DEFAULT_PRESENTATION.users[0].name)
        // then
        assertThat(uiState.filteredScreenPresentation.value).isEqualTo(
           DEFAULT_PRESENTATION.copy(
               users = listOf(DEFAULT_PRESENTATION.users[0])
           ))
    }

    @Test
    fun `given email, when search, then show screen with filteredData`() {
        // given
        uiState.showScreen(userList = DEFAULT_PRESENTATION.users)
        uiState.search(queryText = DEFAULT_PRESENTATION.users[1].email)
        // then
        assertThat(uiState.filteredScreenPresentation.value).isEqualTo(
            DEFAULT_PRESENTATION.copy(
                users = listOf(DEFAULT_PRESENTATION.users[1])
            ))
    }

    private fun appendList(actualList: List<User>, newUserList: List<User>): List<User> {
        return (actualList + newUserList).distinctBy { user -> user.id }
    }


    // Helpers

    private companion object {
        val ERROR_MOCK = Error.mock()
        val DEFAULT_PRESENTATION = UsersListUiState.Presentation(
            users = User.mockList(),
            isPaginating = false,
            isRefreshing = false,
            toastError = null,
            errorLoadingMore = false
        )

    }
}