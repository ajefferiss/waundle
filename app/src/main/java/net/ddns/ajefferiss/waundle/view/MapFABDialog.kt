package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.util.GOOGLE_MAP_TYPES_BY_NAME
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.mapType
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.sharedPreferences

@Composable
fun MapFABDialog(onCloseClicked: () -> Unit) {
    val prefs = sharedPreferences(LocalContext.current)
    var mapTypeExpanded by remember { mutableStateOf(false) }
    var selectedMapType by remember { mutableIntStateOf(GOOGLE_MAP_TYPES_BY_NAME.indexOf(prefs.mapType)) }

    val inlineContentId = "inlineContent"
    val text = buildAnnotatedString {
        append(GOOGLE_MAP_TYPES_BY_NAME[selectedMapType])
        appendInlineContent(inlineContentId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            inlineContentId,
            InlineTextContent(
                Placeholder(
                    width = 12.sp,
                    height = 12.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.AboveBaseline
                )
            ) {
                Icon(Icons.Filled.ArrowDropDown, null)
            }
        )
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Row {
                WaundleTextField(text = stringResource(id = R.string.select_map_type))
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .clickable(onClick = { mapTypeExpanded = true }),
                    inlineContent = inlineContent,
                )
                DropdownMenu(
                    expanded = mapTypeExpanded,
                    onDismissRequest = { mapTypeExpanded = false }) {
                    GOOGLE_MAP_TYPES_BY_NAME.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            text = { WaundleTextField(text = s) },
                            onClick = {
                                selectedMapType = index
                                mapTypeExpanded = false
                                prefs.mapType = s
                            }
                        )
                    }
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                onClick = { onCloseClicked() },
                modifier = Modifier.padding(2.dp)
            ) {
                WaundleTextField(text = stringResource(id = R.string.close))
            }
        }
    }
}