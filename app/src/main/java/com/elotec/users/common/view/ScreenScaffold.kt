package com.elotec.users.common.view

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.elotec.users.common.ScreenState
import com.elotec.users.ui.theme.UsersTheme
import com.elotec.users.utils.error.Error

val DefaultSurfaceColor = Color.White

@Composable
fun ScreenScaffold(
    state: ScreenState = ScreenState.ScreenContent,
    surfaceColor: Color = DefaultSurfaceColor,
    progress: @Composable () -> Unit = {},
    error: @Composable (Error) -> Unit = {},
    content: @Composable () -> Unit,
) = UsersTheme {
    Surface(color = surfaceColor) {
        when (state) {
            is ScreenState.ScreenError -> error(state.error)
            ScreenState.ScreenProgress -> progress()
            ScreenState.ScreenContent -> content()
        }
    }
}