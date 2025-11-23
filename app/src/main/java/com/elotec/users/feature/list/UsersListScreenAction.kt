package com.elotec.users.feature.list

sealed class UsersListScreenAction {
    data object ErrorButtonAction : UsersListScreenAction()

    data object ErrorCloseButtonAction : UsersListScreenAction()

    data object GoToUserDetailAction : UsersListScreenAction()

    data object PaginateAction : UsersListScreenAction()

    data class OnSearchTextChangedAction(val text: String) : UsersListScreenAction()

}

@Suppress
fun UsersListScreenAction.fold(
    errorButtonAction: () -> Unit,
    errorCloseButtonAction: () -> Unit,
    goToUserDetailAction: () -> Unit,
    paginateAction: () -> Unit,
    onSearchTextChangedAction: (String) -> Unit,
): Unit =
    when (this) {
        UsersListScreenAction.ErrorCloseButtonAction -> errorCloseButtonAction()
        UsersListScreenAction.ErrorButtonAction -> errorButtonAction()
        UsersListScreenAction.GoToUserDetailAction -> goToUserDetailAction()
        UsersListScreenAction.PaginateAction -> paginateAction()
        is UsersListScreenAction.OnSearchTextChangedAction -> onSearchTextChangedAction(this.text)
    }