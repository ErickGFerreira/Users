package com.elotec.users.feature.details

import com.elotec.users.utils.event.UiEvent
import javax.inject.Inject

class UserDetailUiEvent @Inject constructor() : UiEvent<UserDetailUiEvent.Event>() {
    sealed interface Event {
        data object Back : Event
    }
}