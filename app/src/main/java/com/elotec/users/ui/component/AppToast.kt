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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.elotec.users.ui.color.BrandPrimary48
import com.elotec.users.ui.color.Error70
import com.elotec.users.ui.color.Neutral100
import com.elotec.users.ui.dimen.Spacing
import com.elotec.users.ui.theme.FontStyle
import kotlinx.coroutines.delay

@Composable
fun AppToast(
    modifier: Modifier = Modifier,
    message: String,
    visible: Boolean,
    buttonText: String,
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
                .background(Error70)
                .padding(Spacing.Nano),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(all = Spacing.XS),
                color = Color.White,
                style = FontStyle.bodyMedium,
                text = message,
            )
            Button(
                modifier = Modifier
                    .padding(all = Spacing.XS),
                colors = ButtonDefaults.buttonColors(containerColor = Neutral100),
                onClick = onAction
            ) {
                Text(
                    color = Color.Black,
                    style = FontStyle.bodyMedium,
                    text = buttonText,
                )
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