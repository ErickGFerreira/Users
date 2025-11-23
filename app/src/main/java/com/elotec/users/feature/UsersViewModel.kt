package com.elotec.users.feature

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.elotec.users.common.viewmodel.BaseFlowData
import com.elotec.users.common.viewmodel.FlowDataSavedStateHandle
import com.elotec.users.common.viewmodel.SavedStateViewModelAssistedFactory
import com.elotec.users.domain.model.User
import com.elotec.users.feature.UsersViewModel.FlowData.Companion.SAVED_STATE_KEY
import javax.inject.Inject

class UsersViewModelFactory @Inject constructor(
    private val navigationEvent: UsersUiEvent,
) : SavedStateViewModelAssistedFactory<UsersViewModel> {
    override fun create(handle: SavedStateHandle) =
        UsersViewModel(
            handle = FlowDataSavedStateHandle(handle = handle, key =    SAVED_STATE_KEY),
            uiEvent = navigationEvent,
        )
}

class UsersViewModel @Inject constructor(
    private val handle: FlowDataSavedStateHandle<FlowData>,
    val uiEvent: UsersUiEvent,
) : ViewModel() {
    var flowData = FlowData()

    fun setup() {
    }

    fun startDestination(): UsersUiEvent.Navigation =
        UsersUiEvent.Navigation.UsersList

    fun navigate(navigation: UsersUiEvent.Navigation) {
        uiEvent.send(event = navigation)
    }

    data class FlowData(
        var users: MutableList<User> = mutableListOf()
    ) : BaseFlowData {
        companion object {
            const val SAVED_STATE_KEY = "[User.FlowData.SavedState]"
        }
    }
}