package com.elotec.users.feature.list

import com.elotec.users.domain.model.User
import com.elotec.users.utils.event.UiEvent
import javax.inject.Inject

class UsersListUiEvent @Inject constructor() :
    UiEvent<UsersListUiEvent.Event>() {
    sealed interface Event {
        data object Finish : Event

        data class NavigateToUserDetail(
            val user: User
        ) : Event


    }
}