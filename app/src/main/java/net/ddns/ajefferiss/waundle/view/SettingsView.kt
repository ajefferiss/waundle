package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsRadioButton
import com.google.maps.android.compose.MapType
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.DataStoreDefaults
import net.ddns.ajefferiss.waundle.data.GOOGLE_MAP_TYPES_BY_NAME
import net.ddns.ajefferiss.waundle.data.GOOGLE_MAP_TYPES_MAP
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.WaundlePreferencesHelper

@Composable
fun SettingsView(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: WaundleViewModel,
    prefs: WaundlePreferencesHelper
) {
    val currentPrefs = remember { mutableStateOf(prefs.getPrefs()) }
    val showMapTypeDialog = remember { mutableStateOf(false) }
    val showNearbyDistanceDialog = remember { mutableStateOf(false) }
    val showResetProgress = remember { mutableStateOf(false) }
    val showBaggingDistanceDialog = remember { mutableStateOf(false) }
    val showResetSettings = remember { mutableStateOf(false) }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.settings),
        showBottomBar = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            SettingsGroup(
                title = { Text(text = stringResource(id = R.string.map_settings)) }
            ) {
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.map_type)) },
                    subtitle = { Text(text = currentPrefs.value.mapType.name) },
                    onClick = { showMapTypeDialog.value = true }
                )
            }

            SettingsGroup(
                title = { Text(text = stringResource(id = R.string.nearby_hills)) }
            ) {
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.nearby_search_distance)) },
                    subtitle = { Text(text = "${currentPrefs.value.nearbyDistance} (m)") },
                    onClick = { showNearbyDistanceDialog.value = true }
                )
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.bagging_distance)) },
                    subtitle = { Text(text = "${currentPrefs.value.baggingDistance} (m)") },
                    onClick = { showBaggingDistanceDialog.value = true }
                )
            }

            SettingsGroup(
                title = { Text(text = stringResource(id = R.string.advanced_settings)) }
            ) {
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.advanced_settings_desc)) }
                ) {}
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.reset_walked_progress)) },
                    onClick = { showResetProgress.value = true }
                )
                SettingsMenuLink(
                    title = { Text(text = stringResource(id = R.string.reset_settings)) },
                    onClick = { showResetSettings.value = true }
                )
            }
        }
    }

    if (showMapTypeDialog.value) {
        MapTypeDialog(
            onDismissRequest = { showMapTypeDialog.value = false },
            onConfirmation = {
                showMapTypeDialog.value = false
                val newPrefs = currentPrefs.value.copy(mapType = it)
                prefs.updatePrefs(newPrefs)
                currentPrefs.value = newPrefs
            },
            selectedItem = currentPrefs.value.mapType
        )
    }

    if (showNearbyDistanceDialog.value) {
        NumberPrefDialog(
            onDismissRequest = { showNearbyDistanceDialog.value = false },
            onConfirmation = {
                showNearbyDistanceDialog.value = false
                val newPrefs = currentPrefs.value.copy(nearbyDistance = it)
                prefs.updatePrefs(newPrefs)
                currentPrefs.value = newPrefs
            },
            initialValue = prefs.getPrefs().nearbyDistance,
            dialogTitle = stringResource(id = R.string.nearby_hills),
            dialogDescription = stringResource(id = R.string.nearby_distance_setting)
        )
    }

    if (showBaggingDistanceDialog.value) {
        NumberPrefDialog(
            onDismissRequest = { showBaggingDistanceDialog.value = false },
            onConfirmation = {
                showBaggingDistanceDialog.value = false
                val newPrefs = currentPrefs.value.copy(baggingDistance = it)
                prefs.updatePrefs(newPrefs)
                currentPrefs.value = newPrefs
            },
            initialValue = prefs.getPrefs().baggingDistance,
            dialogTitle = stringResource(id = R.string.bagging_distance),
            dialogDescription = stringResource(id = R.string.bagging_distance_setting)
        )
    }

    if (showResetProgress.value) {
        AdvancedConfirmationDialog(
            onDismissRequest = { showResetProgress.value = false },
            onConfirmation = {
                showResetProgress.value = false
                viewModel.resetWalkedProgress()
            },
            title = stringResource(id = R.string.reset_walked_hills),
            description = stringResource(id = R.string.reset_progress_dialog_message)
        )
    }

    if (showResetSettings.value) {
        AdvancedConfirmationDialog(
            onDismissRequest = { showResetSettings.value = false },
            onConfirmation = {
                showResetSettings.value = false
                currentPrefs.value = prefs.defaultPrefs()
            },
            title = stringResource(id = R.string.reset_settings),
            description = stringResource(id = R.string.reset_settings_dialog_message)
        )
    }
}

@Composable
fun BaseSettingsDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    content: @Composable () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(shape = RoundedCornerShape(16.dp)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = title, fontWeight = FontWeight.SemiBold)
                }
                content()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.ok))
                    }
                }
            }
        }
    }
}

@Composable
fun MapTypeDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (MapType) -> Unit,
    selectedItem: MapType
) {
    val state = remember { mutableStateOf(selectedItem.name) }

    BaseSettingsDialog(
        onDismissRequest = onDismissRequest,
        onConfirmation = {
            onConfirmation(
                GOOGLE_MAP_TYPES_MAP.getOrDefault(
                    state.value,
                    DataStoreDefaults.mapType
                )
            )
        },
        title = stringResource(id = R.string.map_type)
    ) {
        GOOGLE_MAP_TYPES_BY_NAME.forEach { mapType ->
            Row {
                SettingsRadioButton(
                    state = state.value == mapType,
                    title = { Text(text = mapType) },
                    onClick = {
                        state.value = mapType
                    }
                )
            }
        }
    }
}

@Composable
fun NumberPrefDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Int) -> Unit,
    initialValue: Int,
    dialogTitle: String,
    dialogDescription: String
) {
    val perfValue = remember { mutableIntStateOf(initialValue) }
    val numberPattern = remember { Regex("^\\d+\$") }

    BaseSettingsDialog(
        onDismissRequest = onDismissRequest,
        onConfirmation = { onConfirmation(perfValue.intValue) },
        title = dialogTitle
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = dialogDescription)
        }
        OutlinedTextField(
            value = perfValue.intValue.toString(),
            onValueChange = {
                if (it.isNotEmpty() && it.matches(numberPattern)) {
                    perfValue.intValue = Integer.valueOf(it)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun AdvancedConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    description: String
) {
    BaseSettingsDialog(
        onDismissRequest = onDismissRequest,
        onConfirmation = { onConfirmation() },
        title = title
    ) {
        Text(text = description)
    }
}