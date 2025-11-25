package com.elotec.users.feature.list

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.colors
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.util.query
import com.elotec.users.R
import com.elotec.users.common.view.ScreenScaffold
import com.elotec.users.domain.model.LabelValueData
import com.elotec.users.feature.UsersViewModel
import com.elotec.users.feature.list.UsersListScreenAction.ErrorButtonAction
import com.elotec.users.feature.list.UsersListScreenAction.ErrorCloseButtonAction
import com.elotec.users.feature.list.UsersListScreenAction.PaginateAction
import com.elotec.users.feature.list.UsersListUiEvent.Event.Finish
import com.elotec.users.ui.color.Neutral100
import com.elotec.users.ui.color.Neutral58
import com.elotec.users.ui.component.AppToast
import com.elotec.users.ui.component.EmptyScreen
import com.elotec.users.ui.component.ScreenError
import com.elotec.users.ui.component.SpacerVertical
import com.elotec.users.ui.component.UserCard
import com.elotec.users.utils.GENERIC_ERROR
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun UsersListScreen(
    viewModel: UsersListViewModel,
    flowData: UsersViewModel.FlowData,
    navigateToUserDetails: () -> Unit,
) {
    val activity = LocalActivity.current as FragmentActivity

    LaunchedEffect(key1 = Unit) {
        viewModel.setup(flowData = flowData)
    }
    Screen(
        uiState = viewModel.uiState,
        onActionEvent = viewModel::onActionEvent,
    )
    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        navigateToUsersDetails = navigateToUserDetails,
    )
}

@Composable
private fun EventConsumer(
    activity: FragmentActivity,
    viewModel: UsersListViewModel,
    navigateToUsersDetails: () -> Unit,
) = LaunchedEffect(key1 = Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            Finish -> activity.finish()
            is UsersListUiEvent.Event.NavigateToUserDetail -> navigateToUsersDetails()
        }
    }
}

@Composable
private fun Screen(
    onActionEvent: (UsersListScreenAction) -> Unit,
    uiState: UsersListUiState,
) {
    ScreenScaffold(
        state = uiState.screenState.collectAsStateWithLifecycle().value,
        progress = { ScreenProgress() },
        error = {
            ScreenError(
                title = it.title,
                message = it.message,
                onTryAgainClick = { onActionEvent(ErrorButtonAction) },
                onCloseClick = { onActionEvent(ErrorCloseButtonAction) }
            )
        },
        content = {
            ScreenContent(
                onActionEvent = onActionEvent,
                uiState = uiState
            )
        })
}

@Composable
private fun ScreenProgress() {
    Column(
        modifier =
            Modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    onActionEvent: (UsersListScreenAction) -> Unit,
    uiState: UsersListUiState
) {
    val presentation by uiState.filteredScreenPresentation.collectAsStateWithLifecycle()
    val query by uiState.query.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral58)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars),
            value = query,
            onValueChange = {
                onActionEvent(UsersListScreenAction.OnSearchTextChangedAction(text = it))
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.serach_placeholder)
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            colors = colors(
                unfocusedContainerColor = Neutral100,
                focusedContainerColor = Neutral100
            )
        )
        PullToRefreshBox(
            isRefreshing = presentation.isRefreshing,
            onRefresh = { onActionEvent(UsersListScreenAction.RefreshAction) },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                TrackingCardList(
                    presentation = presentation,
                    onActionEvent = onActionEvent,
                    query = query,
                )
                AppToast(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    message = presentation.toastError?.title ?: GENERIC_ERROR,
                    visible = presentation.errorLoadingMore,
                    buttonText = stringResource(R.string.try_again),
                    onAction = { onActionEvent(UsersListScreenAction.ErrorPaginating) },
                    onDismiss = { onActionEvent(UsersListScreenAction.OnToastDismissedAction) }
                )
            }
        }
    }
}

@Composable
private fun TrackingCardList(
    presentation: UsersListUiState.Presentation,
    onActionEvent: (UsersListScreenAction) -> Unit,
    query: String,
) {
    val lazyListState = rememberLazyListState()

    val shouldPaginate = remember {
        derivedStateOf {
            val totalItems = lazyListState.layoutInfo.totalItemsCount
            val lastVisibleItemIndex =
                lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex == totalItems - 1
        }
    }

    LaunchedEffect(key1 = lazyListState, key2 = query) {
        snapshotFlow { shouldPaginate.value }
            .distinctUntilChanged()
            .filter { it && query.isEmpty() }
            .collect { onActionEvent(PaginateAction) }
    }
    if (presentation.users.isEmpty() && query.isNotEmpty()) {
        EmptyScreen(message = stringResource(R.string.empty_list))
    } else if (presentation.isPaginating) {
        CircularProgressIndicator()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState,
        contentPadding = PaddingValues(all = 12.dp)
    ) {
        items(
            items = presentation.users,
            key = { users -> users.id }) { user ->
            UserCard(
                nameInfo = LabelValueData(
                    label = stringResource(R.string.user_name),
                    value = user.name
                ),
                cityInfo = LabelValueData(
                    label = stringResource(R.string.user_city),
                    value = user.address.city
                ),
                emailInfo = LabelValueData(
                    label = stringResource(R.string.user_email),
                    value = user.email
                ),
            )
            SpacerVertical()
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    Screen(
        onActionEvent = {},
        uiState = UsersListUiState().apply {
        }
    )
}
