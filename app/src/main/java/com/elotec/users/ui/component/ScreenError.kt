package com.elotec.users.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.elotec.users.R
import com.elotec.users.ui.dimen.Size
import com.elotec.users.ui.dimen.Spacing

@Composable
fun ScreenError(
    title: String,
    onTryAgainClick: () -> Unit,
    onCloseClick: () -> Unit,
    message: String,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier.fillMaxWidth(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = Spacing.SM)
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = Size.Tower)
                .padding(
                    horizontal = Spacing.XL,
                    vertical = Spacing.MD
                )
        ) {
            ->
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(
                    id = R.drawable.ic_launcher_background
                ),
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
//        SpacerVertical()
//        Text(
//            modifier = Modifier
//                .padding(horizontal = Spacing.SM),
//            text = title,
//            style = Typography.bodyMedium,
//            textAlign = TextAlign.Center
//        )
//        SpacerVertical(dp = Size.Size4)
//        Text(
//            modifier = Modifier,
//            text = message,
//            style = Typography.bodyMedium,
//            color = Color.Black,
//            textAlign = TextAlign.Center
//        )
//        SpacerVertical(dp = Spacing.XL)
//        Button(onClick = onTryAgainClick) {
//            Text("Tentar Novamente")
//        }
//        SpacerVertical(dp = Spacing.SM)
//        Button(onClick = onCloseClick) {
//            Text("Sair")
//        }
    }
}