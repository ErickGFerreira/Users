package com.elotec.users.feature.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elotec.users.domain.model.User
import com.elotec.users.domain.usecase.GetRemotePaginatedUsersUseCase
import com.elotec.users.domain.usecase.UsersListUseCase
import com.elotec.users.feature.UsersViewModel
import com.elotec.users.utils.safe.fold
import kotlinx.coroutines.launch
import javax.inject.Inject


class UsersListViewModel @Inject constructor(
    private val userListUseCase: UsersListUseCase,
    private val getRemotePaginatedUsersUseCase: GetRemotePaginatedUsersUseCase,
    val uiState: UsersListUiState,
    val uiEvent: UsersListUiEvent,
) : ViewModel() {

    @VisibleForTesting
    var flowData = UsersViewModel.FlowData()
    @VisibleForTesting
    internal var page: Int = 1

    fun setup(flowData: UsersViewModel.FlowData) {
        this.flowData = flowData
        getUserList()
    }

    fun onActionEvent(action: UsersListScreenAction) =
        action.fold(
            errorButtonAction = { getUserList() },
            errorCloseButtonAction = ::finish,
            paginateAction = { loadMoreUsers(currentPage = page) },
            refreshAction = { reloadUserList() },
            errorPaginating = { loadMoreUsers(currentPage = page) },
            onSearchTextChangedAction = uiState::search,
            onToastDismissedAction = uiState::dismissToast,
            onUserClicked = ::navigateToUserDetails
        )

    @VisibleForTesting
    fun reloadUserList() {
        viewModelScope.launch {
            page = 1
            uiState.onRefreshing(isRefreshing = true)
            userListUseCase.execute().fold(
                onSuccess = { userList ->
                    uiState.showScreen(userList = userList)
                },
                onFailure = uiState::showToastError,
            )
        }
    }

    @VisibleForTesting
    fun getUserList() {
        viewModelScope.launch {
            uiState.showProgress()
            userListUseCase.execute().fold(
                onSuccess = { userList ->
                    uiState.showScreen(userList = userList)
                },
                onFailure = uiState::showError,
            )
        }
    }

    @VisibleForTesting
    fun loadMoreUsers(currentPage: Int) {
        viewModelScope.launch {
            uiState.dismissToast()
            uiState.onPaginating(isPaginating = true)
            getRemotePaginatedUsersUseCase.execute(page = currentPage).fold(
                onSuccess = { userList ->
                    if (userList.isNotEmpty()) {
                        page++
                        uiState.appendUsers(userList = userList)
                    } else {
                        uiState.onPaginating(isPaginating = false)
                    }
                },
                onFailure = { error ->
                    uiState.showToastError(error)
                }
            )
        }
    }

    private fun navigateToUserDetails(user: User) {
        uiEvent.send(event = UsersListUiEvent.Event.NavigateToUserDetail(user = user))
    }

    private fun finish() {
        uiEvent.send(event = UsersListUiEvent.Event.Finish)
    }
}