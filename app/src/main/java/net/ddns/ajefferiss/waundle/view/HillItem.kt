package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.Hill

@Composable
fun HillItem(hill: Hill, onClick: () -> Unit) {

    var hillDescription =
        stringResource(id = R.string.hill_desc_height) + ": " + hill.feet + "(ft), " + hill.metres + "(m)"
    if (hill.climbed != null) {
        hillDescription += "\n" + stringResource(id = R.string.hill_walked_on) + " " + hill.climbed.toString()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            WaundleTextField(text = hill.name, fontWeight = FontWeight.ExtraBold)
            WaundleTextField(text = hillDescription, fontWeight = FontWeight.SemiBold)
        }
    }
}