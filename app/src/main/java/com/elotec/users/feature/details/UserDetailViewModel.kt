package com.elotec.users.feature.details

import androidx.lifecycle.ViewModel
import com.elotec.users.feature.UsersViewModel
import javax.inject.Inject

class UserDetailViewModel @Inject constructor(
    val uiState: UserDetailUiState,
    val uiEvent: UserDetailUiEvent,
) : ViewModel() {
    fun setup(flowData: UsersViewModel.FlowData) {
        uiState.showUserDetail(user = flowData.user)
    }

    fun onActionEvent(action: UserDetailScreenAction) =
        action.fold(
            backButtonAction = ::back,
        )

    private fun back() {
        uiEvent.send(event = UserDetailUiEvent.Event.Back)
    }
}