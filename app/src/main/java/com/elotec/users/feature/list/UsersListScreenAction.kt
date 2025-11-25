package com.elotec.users.feature.list

sealed class UsersListScreenAction {
    data object ErrorButtonAction : UsersListScreenAction()

    data object ErrorCloseButtonAction : UsersListScreenAction()

    data object GoToUserDetailAction : UsersListScreenAction()

    data object PaginateAction : UsersListScreenAction()

    data object RefreshAction : UsersListScreenAction()

    data object ErrorPaginating : UsersListScreenAction()

    data object OnToastDismissedAction : UsersListScreenAction()

    data class OnSearchTextChangedAction(val text: String) : UsersListScreenAction()

}

@Suppress
fun UsersListScreenAction.fold(
    errorButtonAction: () -> Unit,
    errorCloseButtonAction: () -> Unit,
    goToUserDetailAction: () -> Unit,
    paginateAction: () -> Unit,
    refreshAction: () -> Unit,
    errorPaginating: () -> Unit,
    onToastDismissedAction: () -> Unit,
    onSearchTextChangedAction: (String) -> Unit,
): Unit =
    when (this) {
        UsersListScreenAction.ErrorCloseButtonAction -> errorCloseButtonAction()
        UsersListScreenAction.ErrorButtonAction -> errorButtonAction()
        UsersListScreenAction.GoToUserDetailAction -> goToUserDetailAction()
        UsersListScreenAction.PaginateAction -> paginateAction()
        UsersListScreenAction.RefreshAction -> refreshAction()
        UsersListScreenAction.ErrorPaginating -> errorPaginating()
        UsersListScreenAction.OnToastDismissedAction -> onToastDismissedAction()
        is UsersListScreenAction.OnSearchTextChangedAction -> onSearchTextChangedAction(this.text)
    }