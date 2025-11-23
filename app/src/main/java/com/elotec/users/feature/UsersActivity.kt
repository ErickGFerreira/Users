package com.elotec.users.feature

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.elotec.users.common.navigation.customNavigate
import com.elotec.users.common.navigation.setNavigationContent
import com.elotec.users.common.view.BaseComposeActivity
import com.elotec.users.common.viewmodel.SavedStateViewModelFactory
import com.elotec.users.feature.UsersUiEvent.Navigation.Finish
import com.elotec.users.feature.list.UsersListScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersActivity : BaseComposeActivity<UsersViewModel>() {
    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory

    override fun viewModelClass() = UsersViewModel::class.java

    override fun createFlowViewModelFactory(): ViewModelProvider.Factory =
        SavedStateViewModelFactory(
            viewModelFactory = usersViewModelFactory,
            owner = this,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState = savedInstanceState)
        flowViewModel.setup()
        setNavigationContent(
            startDestination = flowViewModel.startDestination().route,
            navGraphBuilder = this::navGraphBuilder,
            navEventFlow = flowViewModel.uiEvent.eventsFlow,
            navEvent = this::navEvent,
        )
    }

    @Suppress
    private fun navGraphBuilder(builder: NavGraphBuilder) =
        builder.apply {
            composable(route = UsersUiEvent.Navigation.UsersList.route) {
                UsersListScreen(
                    viewModel = composeViewModel(),
                    flowData = flowViewModel.flowData,
                    navigateToUserDetails = {}
                )
            }
        }

    private fun navEvent(
        navController: NavHostController,
        navScreen: UsersUiEvent.Navigation,
    ) {
        when (navScreen) {
            is Finish -> finish()
            else -> navController.customNavigate(route = navScreen.route)
        }
    }

}