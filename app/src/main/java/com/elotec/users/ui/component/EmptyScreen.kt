package com.elotec.users.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.elotec.users.ui.color.Neutral0
import com.elotec.users.ui.theme.FontStyle

@Composable
fun EmptyScreen(
    message: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = FontStyle.bodyMedium,
            color = Neutral0
        )
    }
}