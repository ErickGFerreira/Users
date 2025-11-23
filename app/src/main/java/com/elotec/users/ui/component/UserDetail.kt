package com.elotec.users.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.elotec.users.domain.model.LabelValueData
import com.elotec.users.ui.color.Neutral69
import com.elotec.users.ui.dimen.Spacing

@Composable
fun UserDetail(
    modifier: Modifier = Modifier,
    values: List<LabelValueData>,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Spacing.Micro),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(Spacing.Micro)
    ) {
        items(values.size) { index ->
            LabelValue(
                modifier = modifier,
                label = LabelValueText(
                    text = values[index].label,
                    textStyle = TextStyle.Default.copy(fontWeight = FontWeight.Bold),
                    color = Neutral69
                ),
                value = LabelValueText(
                    text = values[index].value,
                    textStyle = TextStyle.Default
                )
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {
}
