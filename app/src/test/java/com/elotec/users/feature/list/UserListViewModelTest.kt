package com.elotec.users.feature.list

import com.elotec.users.common.test.CoroutinesTestRule
import com.elotec.users.domain.model.User
import com.elotec.users.domain.usecase.UsersListUseCase
import com.elotec.users.feature.UsersViewModel
import com.elotec.users.feature.list.UsersListScreenAction.ErrorButtonAction
import com.elotec.users.feature.list.UsersListScreenAction.ErrorCloseButtonAction
import com.elotec.users.feature.list.UsersListScreenAction.ErrorPaginating
import com.elotec.users.feature.list.UsersListScreenAction.OnSearchTextChangedAction
import com.elotec.users.feature.list.UsersListScreenAction.OnToastDismissedAction
import com.elotec.users.feature.list.UsersListScreenAction.OnUserClicked
import com.elotec.users.feature.list.UsersListScreenAction.PaginateAction
import com.elotec.users.feature.list.UsersListScreenAction.RefreshAction
import com.elotec.users.feature.list.UsersListUiEvent.Event.Finish
import com.elotec.users.feature.list.UsersListUiEvent.Event.NavigateToUserDetail
import com.elotec.users.utils.error.ErrorHandler
import com.elotec.users.utils.safe.Result
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class UsersListViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val usersListUseCase: UsersListUseCase =
        mockk(relaxed = true)
    private val uiState: UsersListUiState = mockk(relaxed = true)

    private val uiEvent: UsersListUiEvent = mockk(relaxed = true)

    private lateinit var viewModel: UsersListViewModel

    @Before
    fun setup() {
        viewModel =
            UsersListViewModel(
                userListUseCase = usersListUseCase,
                uiState = uiState,
                uiEvent = uiEvent,
            )
    }

    @Test
    fun `given usersListUseCase success, when setup, then show screen with data`() =
        coroutinesTestRule.runTest {
            // Given
            coEvery {
                usersListUseCase.execute()
            } returns Result.Success(data = USER_LIST)

            // When
            viewModel.setup(flowData = FLOW_DATA)

            // Then
            assertThat(viewModel.flowData).isEqualTo(FLOW_DATA)
            coVerifyOrder {
                uiState.showProgress()
                usersListUseCase.execute()
                uiState.showScreen(userList = USER_LIST)
            }
        }

    @Test
    fun `given usersListUseCase error, when setup, then show error`() =
        coroutinesTestRule.runTest {
            // Given
            coEvery {
                usersListUseCase.execute()
            } returns Result.Failure(error = ERROR_MOCK)
            viewModel.flowData = FLOW_DATA

            // When

            viewModel.setup(flowData = FLOW_DATA)

            // Then
            coVerifyOrder {
                uiState.showProgress()
                usersListUseCase.execute()
                uiState.showError(error = ERROR_MOCK)
            }
        }

    @Test
    fun `given getRemotePaginatedUsersUseCase success, when paginate action with data, then append list`() =
        coroutinesTestRule.runTest {
            val page = 1
            // Given
            coEvery {
                usersListUseCase.execute(any())
            } returns Result.Success(data = USER_LIST)

            // When
            viewModel.onActionEvent(PaginateAction)

            assertEquals(viewModel.page, page + 1)
            // Then
            coVerifyOrder {
                uiState.dismissToast()
                uiState.onPaginating(isPaginating = true)
                usersListUseCase.execute(page)
                uiState.appendUsers(userList = USER_LIST)
            }
        }

    @Test
    fun `given getRemotePaginatedUsersUseCase success, when paginate action with empty list, then just stop pagination`() =
        coroutinesTestRule.runTest {
            val page = viewModel.page
            // Given
            coEvery {
                usersListUseCase.execute(any())
            } returns Result.Success(data = emptyList())

            // When
            viewModel.onActionEvent(PaginateAction)

            assertEquals(viewModel.page, page)
            // Then
            coVerifyOrder {
                uiState.dismissToast()
                uiState.onPaginating(isPaginating = true)
                usersListUseCase.execute(page)
                uiState.onPaginating(isPaginating = false)
            }
        }

    @Test
    fun `given getRemotePaginatedUsersUseCase error, when paginate action, then show toast error`() =
        coroutinesTestRule.runTest {
            // Given
            coEvery {
                usersListUseCase.execute(any())
            } returns Result.Failure(error = ERROR_MOCK)

            // When

            viewModel.onActionEvent(PaginateAction)

            // Then
            coVerifyOrder {
                uiState.dismissToast()
                uiState.onPaginating(isPaginating = true)
                usersListUseCase.execute(PAGE)
                uiState.showToastError(error = ERROR_MOCK)
            }
        }

    @Test
    fun `given ErrorButtonAction, when onActionEvent, then retry get user list`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = ErrorButtonAction)

            // Then
            coVerifyOrder {
                usersListUseCase.execute()
            }
        }

    @Test
    fun `given ErrorCloseButtonAction, when onActionEvent, then finish`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = ErrorCloseButtonAction)

            // Then
            coVerifyOrder {
                uiEvent.send(event = Finish)
            }
        }

    @Test
    fun `given PaginateAction, when onActionEvent, then load more users`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = PaginateAction)

            // Then
            coVerifyOrder {
                usersListUseCase.execute(currentPage = any())
            }
        }

    @Test
    fun `given RefreshAction, when onActionEvent, then refreshList with first page`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = RefreshAction)

            // Then
            coVerifyOrder {
                usersListUseCase.execute()
            }
        }

    @Test
    fun `given ErrorPaginating, when onActionEvent, then try to get more users again`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = ErrorPaginating)

            // Then
            coVerifyOrder {
                usersListUseCase.execute(currentPage = any())
            }
        }

    @Test
    fun `given onSearchTextChangedAction, when onActionEvent, then search`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = OnSearchTextChangedAction(text = SEARCH_TEXT))

            // Then
            coVerifyOrder {
                uiState.search(queryText = SEARCH_TEXT)
            }
        }

    @Test
    fun `given onToastDismissedAction, when onActionEvent, then dismiss toast`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = OnToastDismissedAction)

            // Then
            coVerifyOrder {
                uiState.dismissToast()
            }
        }

    @Test
    fun `given onUserClicked, when onActionEvent, go to user detail`() =
        coroutinesTestRule.runTest {
            // When
            viewModel.onActionEvent(action = OnUserClicked(USER))

            // Then
            coVerifyOrder {
                uiEvent.send(event = NavigateToUserDetail(user = USER))
            }
        }


    // Helpers

    private companion object {
        val USER_LIST = User.mockList()

        val USER = User.mockUser()
        const val SEARCH_TEXT = "TEXT"
        const val PAGE = 1
        val ERROR_MOCK = ErrorHandler.connectivityError
        val FLOW_DATA =
            UsersViewModel.FlowData(
                user = User.mockUser(),
            )
    }
}
