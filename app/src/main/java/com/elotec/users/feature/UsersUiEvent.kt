package com.elotec.users.feature

import com.elotec.users.utils.EMPTY_STRING
import com.elotec.users.utils.event.UiEvent
import javax.inject.Inject

class UsersUiEvent @Inject constructor() : UiEvent<UsersUiEvent.Navigation>() {
    sealed class Navigation(
        val route: String = EMPTY_STRING,
    ) {
        data object UsersList : Navigation(route = "users-list")

        data object UserDetail: Navigation(route = "user-detail")

        data object Finish : Navigation()
    }
}