package com.elotec.users.feature.list

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elotec.users.domain.usecase.GetRemoteUserListUseCase
import com.elotec.users.domain.usecase.UserListUseCase
import com.elotec.users.feature.UsersViewModel
import com.elotec.users.utils.safe.fold
import kotlinx.coroutines.launch
import javax.inject.Inject


class UsersListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    val uiState: UsersListUiState,
    val uiEvent: UsersListUiEvent,
) : ViewModel() {

    fun setup(flowData: UsersViewModel.FlowData) {
        getUserList()
    }

    fun onActionEvent(action: UsersListScreenAction) =
        action.fold(
            errorButtonAction = { getUserList() },
            errorCloseButtonAction = ::finish,
            goToUserDetailAction = {},
            paginateAction = {},
            onSearchTextChangedAction = uiState::search
        )

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

    private fun finish() {
        uiEvent.send(event = UsersListUiEvent.Event.Finish)
    }
}