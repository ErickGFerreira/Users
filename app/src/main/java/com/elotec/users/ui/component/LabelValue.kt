package com.elotec.users.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.elotec.users.utils.EMPTY_STRING

@Composable
fun LabelValue(
    modifier: Modifier = Modifier,
    label: LabelValueText,
    value: LabelValueText
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = modifier,
            text = label.text,
            color = label.color,
            style = label.textStyle
        )
        Text(
            modifier = modifier,
            text = value.text,
            color = value.color,
            style = value.textStyle
        )
    }
}

data class LabelValueText(
    val text: String,
    val textStyle: TextStyle,
    val color: Color = textStyle.color,
    val testTag: String = EMPTY_STRING
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun Preview() {

}
