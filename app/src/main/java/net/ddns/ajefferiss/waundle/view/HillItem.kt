package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.Hill
import net.ddns.ajefferiss.waundle.util.formatWalkedDate

@Composable
fun HillItem(hill: Hill, onClick: () -> Unit) {
    var hillDescription = stringResource(
        R.string.base_hill_description,
        hill.feet,
        hill.metres
    )

    if (hill.climbed != null) {
        hillDescription = hillDescription.plus("\n").plus(
            stringResource(
                id = R.string.hill_walked_on,
                formatWalkedDate(hill.climbed)
            )
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = hill.name, fontWeight = FontWeight.ExtraBold, color = Color.Black)
            Text(text = hillDescription, fontWeight = FontWeight.SemiBold, color = Color.Black)
        }
    }
}