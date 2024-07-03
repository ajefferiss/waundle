package net.ddns.ajefferiss.waundle.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun WaundleTextField(
    text: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        color = Color.Black,
        modifier = modifier
    )
}