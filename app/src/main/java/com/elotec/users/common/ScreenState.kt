package com.elotec.users.common

import com.elotec.users.utils.error.Error

sealed class ScreenState {
    data object ScreenContent : ScreenState()
    data object ScreenProgress : ScreenState()
    data class ScreenError(val error: Error) : ScreenState()
}