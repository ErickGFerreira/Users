package com.elotec.users.common.navigation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalComposeUiApi::class)
fun <T> ComponentActivity.setNavigationContent(
    startDestination: String,
    navGraphBuilder: NavGraphBuilder.() -> Unit,
    navEventFlow: Flow<T>,
    navEvent: (navController: NavHostController, navScreen: T) -> Unit
) {
    setContent {
        val navController = rememberNavController()
        LaunchedEffect(Unit) {
            navEventFlow.collect { navEvent(navController, it) }
        }
        NavHost(
            modifier = Modifier.semantics { testTagsAsResourceId = true },
            navController = navController,
            startDestination = startDestination,
            builder = navGraphBuilder
        )
    }
}