package com.elotec.users.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.elotec.users.ui.dimen.FontSize

object FontStyle {

    private val DefaultStyle = TextStyle.Default

    internal val bodyMedium = DefaultStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = FontSize.XS
    )

    internal val bodyRegular = DefaultStyle.copy(
        fontSize = FontSize.XS
    )
}