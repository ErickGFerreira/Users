package com.elotec.users.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.elotec.users.ui.dimen.Spacing
import kotlinx.coroutines.delay

@Composable
fun AppToast(
    modifier: Modifier = Modifier,
    message: String,
    visible: Boolean,
    durationMillis: Long = 5000L,
    onAction: () -> Unit,
    onDismiss: () -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = slideInVertically(
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(Spacing.Nano),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .background(Color.Red),
                color = Color.White,
                text = message,
            )
            Button(
                onClick = onAction
            ) {
                Text("Tentar Novamente")
            }
        }
    }
    LaunchedEffect(visible) {
        if (visible) {
            delay(durationMillis)
            onDismiss()
        }
    }
}