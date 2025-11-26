package com.elotec.users.feature.details

sealed class UserDetailScreenAction {
    data object BackButtonAction : UserDetailScreenAction()
}

@Suppress
fun UserDetailScreenAction.fold(
    backButtonAction: () -> Unit,
): Unit =
    when (this) {
        UserDetailScreenAction.BackButtonAction -> backButtonAction()
    }
