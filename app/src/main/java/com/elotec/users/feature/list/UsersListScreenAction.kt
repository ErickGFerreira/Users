package com.elotec.users.feature.list

import com.elotec.users.domain.model.User

sealed class UsersListScreenAction {
    data object ErrorButtonAction : UsersListScreenAction()

    data object ErrorCloseButtonAction : UsersListScreenAction()

    data object PaginateAction : UsersListScreenAction()

    data object RefreshAction : UsersListScreenAction()

    data object ErrorPaginating : UsersListScreenAction()

    data object OnToastDismissedAction : UsersListScreenAction()

    data class OnSearchTextChangedAction(val text: String) : UsersListScreenAction()

    data class OnUserClicked(val user: User) : UsersListScreenAction()

}

@Suppress
fun UsersListScreenAction.fold(
    errorButtonAction: () -> Unit,
    errorCloseButtonAction: () -> Unit,
    paginateAction: () -> Unit,
    refreshAction: () -> Unit,
    errorPaginating: () -> Unit,
    onToastDismissedAction: () -> Unit,
    onSearchTextChangedAction: (String) -> Unit,
    onUserClicked: (User) -> Unit,
): Unit =
    when (this) {
        UsersListScreenAction.ErrorCloseButtonAction -> errorCloseButtonAction()
        UsersListScreenAction.ErrorButtonAction -> errorButtonAction()
        UsersListScreenAction.PaginateAction -> paginateAction()
        UsersListScreenAction.RefreshAction -> refreshAction()
        UsersListScreenAction.ErrorPaginating -> errorPaginating()
        UsersListScreenAction.OnToastDismissedAction -> onToastDismissedAction()
        is UsersListScreenAction.OnSearchTextChangedAction -> onSearchTextChangedAction(this.text)
        is UsersListScreenAction.OnUserClicked -> onUserClicked(this.user)
    }