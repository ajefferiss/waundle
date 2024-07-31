package net.ddns.ajefferiss.waundle.view

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.GOOGLE_MAP_TYPES_BY_NAME
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.mapType
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.nearbyDistance
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.sharedPreferences

@Composable
fun SettingsView(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: WaundleViewModel
) {
    val context = LocalContext.current
    val prefs = sharedPreferences(context)

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.settings)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                NearbyHillSettings(prefs)
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(5.dp))
                MapViewSettings(prefs)
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(5.dp))
                AdvancedSettings(viewModel, context)
            }
        }
    }
}

@Composable
fun NearbyHillSettings(prefs: SharedPreferences) {
    var nearbyDistance by remember { mutableIntStateOf(prefs.nearbyDistance) }

    Column {
        Text(
            text = stringResource(id = R.string.nearby_hill_settings),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(
            text = stringResource(id = R.string.nearby_distance_setting),
            color = Color.Black
        )
        OutlinedTextField(
            value = nearbyDistance.toString(),
            onValueChange = { nb ->
                if (nb.isNotEmpty() && nb.matches(Regex("^\\d+\$"))) {
                    prefs.nearbyDistance = nb.toInt()
                    nearbyDistance = nb.toInt()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun MapViewSettings(prefs: SharedPreferences) {
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

    Column {
        Text(
            text = stringResource(id = R.string.mav_view_settings),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Row {
            Text(
                text = stringResource(id = R.string.select_map_type),
                color = Color.Black,
                modifier = Modifier.padding(end = 5.dp)
            )
            Text(
                text = text,
                modifier = Modifier
                    .padding(end = 2.dp)
                    .clickable(onClick = { mapTypeExpanded = true }),
                inlineContent = inlineContent,
                color = Color.Black
            )
            DropdownMenu(
                expanded = mapTypeExpanded,
                onDismissRequest = { mapTypeExpanded = false }) {
                GOOGLE_MAP_TYPES_BY_NAME.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(text = s) },
                        onClick = {
                            selectedMapType = index
                            mapTypeExpanded = false
                            prefs.mapType = s
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AdvancedSettings(viewModel: WaundleViewModel, context: Context) {

    val confirmationToast = Toast.makeText(
        context,
        stringResource(id = R.string.progress_reset),
        Toast.LENGTH_LONG
    )

    val resetProgressDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(context)
    resetProgressDialogBuilder.setTitle(R.string.reset_progress_dialog_title)
        .setMessage(R.string.reset_progress_dialog_message)
        .setPositiveButton(R.string.reset_progress_confirm) { dialog, which ->
            viewModel.resetWalkedProgress()
            confirmationToast.show()
            dialog.dismiss()
        }
        .setNegativeButton(R.string.close) { dialog, which ->
            dialog.dismiss()
        }
    val resetDialog: AlertDialog = resetProgressDialogBuilder.create()


    Column {
        Text(
            text = stringResource(id = R.string.advanced_settings),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Text(text = stringResource(id = R.string.advanced_settings_warning), color = Color.Black)
        Button(
            onClick = {
                resetDialog.show()
            }
        ) {
            Text(text = stringResource(id = R.string.advanced_reset_walked))
        }
    }
}