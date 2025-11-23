package com.elotec.users.common.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.elotec.users.R

private fun NavOptionsBuilder.setDefaultAnim() {
    anim {
        enter = R.anim.fast_fade_in
        exit = R.anim.fast_fade_out
    }
}

fun NavController.customNavigate(
    route: String,
    popStackType: PopStackType = PopStackType.None,
    launchSingleTop: Boolean = true
) = navigate(route = route) {
    val popRoute = when (popStackType) {
        is PopStackType.StartDestination -> graph.startDestinationRoute
        is PopStackType.CurrentDestination -> currentDestination?.route
        is PopStackType.CustomDestination -> popStackType.route
        else -> null
    }
    if (popRoute != null) popUpTo(popRoute) { inclusive = true }
    this.launchSingleTop = launchSingleTop
    setDefaultAnim()
}

sealed class PopStackType {
    object None : PopStackType()
    object StartDestination : PopStackType()
    object CurrentDestination : PopStackType()
    data class CustomDestination(val route: String) : PopStackType()
}
