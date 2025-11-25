package com.elotec.users.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.elotec.users.R
import com.elotec.users.domain.model.LabelValueData
import com.elotec.users.ui.color.Neutral100
import com.elotec.users.ui.color.Neutral69
import com.elotec.users.ui.dimen.Size
import com.elotec.users.ui.dimen.Spacing
import com.elotec.users.ui.theme.FontStyle

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    nameInfo: LabelValueData,
    cityInfo: LabelValueData,
    emailInfo: LabelValueData,
) {
    Card(
        shape = RoundedCornerShape(Size.Micro),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        elevation = CardDefaults.cardElevation(defaultElevation = Size.nPico)
    ) {
        Column(
            modifier = modifier
                .padding(all = Spacing.XS)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue(
                    label = LabelValueText(
                        text = nameInfo.label,
                        textStyle = FontStyle.bodyMedium,
                        color = Neutral69
                    ),
                    value = LabelValueText(
                        text = nameInfo.value,
                        textStyle = FontStyle.bodyRegular
                    )
                )
                LabelValue(
                    label = LabelValueText(
                        text = cityInfo.label,
                        textStyle = FontStyle.bodyMedium,
                        color = Neutral69,
                    ),
                    value = LabelValueText(
                        text = cityInfo.value,
                        textStyle = FontStyle.bodyRegular,
                    )
                )
            }
            SpacerVertical(dp = Spacing.Micro)
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelValue(
                    label = LabelValueText(
                        text = emailInfo.label,
                        textStyle = FontStyle.bodyMedium,
                        color = Neutral69
                    ),
                    value = LabelValueText(
                        text = emailInfo.value,
                        textStyle = FontStyle.bodyRegular,
                    )
                )
                Image(
                    alignment = Alignment.BottomEnd,
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = null
                )
            }
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {
    UserCard(
        modifier = Modifier,
        nameInfo = LabelValueData(
            label = "Nome",
            value = "Erick"
        ),
        cityInfo = LabelValueData(
            label = "Cidade",
            value = "Cariacica"
        ),
        emailInfo = LabelValueData(
            label = "Email",
            value = "erick@gmail.com"
        )
    )
}
