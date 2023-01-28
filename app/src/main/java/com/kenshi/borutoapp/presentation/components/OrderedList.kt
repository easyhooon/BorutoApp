package com.kenshi.borutoapp.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.kenshi.borutoapp.ui.theme.SMALL_PADDING
import com.kenshi.borutoapp.ui.theme.titleColor

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: Color
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, item ->
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "${index + 1}. $item",
                color = textColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
@Composable
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
fun OrderedListPreview() {
    OrderedList(
        title = "Familiy",
        items = listOf("Minato", "Kushina"),
        textColor = MaterialTheme.colors.titleColor
    )
}

//@Composable
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//fun OrderedListDarkPreview() {
//    OrderedList(
//        title = "Familiy",
//        items = listOf("Minato", "Kushina"),
//        textColor = MaterialTheme.colors.titleColor
//    )
//}