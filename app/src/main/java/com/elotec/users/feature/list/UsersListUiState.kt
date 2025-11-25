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
        val currentState = screenPresentation.value
        filteredScreenPresentation.update { presentation ->
            presentation.copy(
                users = currentState.users.filter { user ->
                    user.name.lowercase().contains(queryText.lowercase()) ||
                            user.email.lowercase().contains(queryText.lowercase())
                },
                isRefreshing = currentState.isRefreshing,
                isPaginating = currentState.isPaginating,
                toastError = currentState.toastError
            )
        }
    }

    fun showToastError(error: Error) {
        screenPresentation.update {
            it.copy(
                toastError = error,
                isRefreshing = false,
                isPaginating = false,
                errorLoadingMore = true,
            )
        }
        filteredScreenPresentation.update {
            it.copy(
                toastError = error,
                isRefreshing = false,
                isPaginating = false,
                errorLoadingMore = true,
            )
        }
    }

    fun dismissToast() {
        screenPresentation.update {
            it.copy(
                errorLoadingMore = false,
            )
        }
        filteredScreenPresentation.update {
            it.copy(
                errorLoadingMore = false,
            )
        }
    }

    fun onRefreshing(isRefreshing: Boolean) {
        screenPresentation.update {
            it.copy(isRefreshing = isRefreshing)
        }
        filteredScreenPresentation.update {
            it.copy(isRefreshing = isRefreshing)
        }
    }

    fun onPaginating(isPaginating: Boolean) {
        screenPresentation.update {
            it.copy(isPaginating = isPaginating)
        }
        filteredScreenPresentation.update {
            it.copy(isPaginating = isPaginating)
        }
    }

    private fun setupUserList(
        userList: List<User>
    ) {
        screenPresentation.update {
            it.copy(
                users = it.users + userList,
                isRefreshing = false,
                isPaginating = false,
                errorLoadingMore = false,
                toastError = null,
            )
        }

        val currentQuery = query.value
        val usersToShow = if (currentQuery.isNotEmpty()) {
            userList.filter { user ->
                user.name.lowercase().contains(currentQuery.lowercase()) ||
                        user.email.lowercase().contains(currentQuery.lowercase())
            }
        } else {
            userList
        }

        filteredScreenPresentation.update {
            it.copy(
                users = usersToShow,
                isPaginating = false,
                isRefreshing = false,
            )
        }
    }

    data class Presentation(
        val users: List<User> = emptyList(),
        val isPaginating: Boolean = false,
        val isRefreshing: Boolean = false,
        val toastError: Error? = null,
        val errorLoadingMore: Boolean = false,
    )
}