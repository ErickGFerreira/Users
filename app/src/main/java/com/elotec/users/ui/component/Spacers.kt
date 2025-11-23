package com.elotec.users.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.elotec.users.ui.dimen.Spacing

@Composable
fun SpacerVertical(dp: Dp = Spacing.SM) {
    Spacer(modifier = Modifier.height(dp))
}