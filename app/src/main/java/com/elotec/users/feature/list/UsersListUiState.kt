package com.elotec.users.feature.list

import com.elotec.users.common.ScreenState
import com.elotec.users.domain.model.User
import com.elotec.users.utils.EMPTY_STRING
import com.elotec.users.utils.error.Error
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class UsersListUiState @Inject constructor() {
    val screenPresentation = MutableStateFlow<Presentation>(Presentation())
    val filteredScreenPresentation = MutableStateFlow<Presentation>(Presentation())
    val screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenContent)
    val query = MutableStateFlow(EMPTY_STRING)

    fun showScreen(
        userList: List<User>,
    ) {
        screenState.value = ScreenState.ScreenContent
        setupUserList(userList = userList)
    }

    fun showProgress() {
        screenState.value = ScreenState.ScreenProgress
    }

    fun showError(error: Error) {
        screenState.value = ScreenState.ScreenError(error = error)
    }

    fun search(queryText: String) {
        query.value = queryText
        filteredScreenPresentation.update { presentation ->
            presentation.copy(users = screenPresentation.value.users.filter { user ->
                user.name.lowercase().contains(queryText.lowercase()) ||
                        user.email.lowercase().contains(queryText.lowercase())
            })
        }
    }

    fun onPaginating(isPaginating: Boolean) {
        screenPresentation.update {
            it.copy(isPaginating = isPaginating)
        }
    }

    private fun setupUserList(
        userList: List<User>
    ) {
        screenPresentation.update {
            it.copy(
                users = userList
            )
        }
        filteredScreenPresentation.value = screenPresentation.value
    }

    data class Presentation(
        val users: List<User> = emptyList(),
        val isPaginating: Boolean = false,
    )
}