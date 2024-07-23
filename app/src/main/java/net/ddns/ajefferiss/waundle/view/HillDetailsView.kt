package net.ddns.ajefferiss.waundle.view

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.MainActivity
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.LocationData
import net.ddns.ajefferiss.waundle.util.LocationUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HillDetailsView(
    id: Long,
    viewModel: WaundleViewModel,
    navController: NavController,
    context: Context,
    drawerState: DrawerState
) {
    val locationUtils = LocationUtils(context)
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    val openDatePickerDialog = remember { mutableStateOf(false) }

    val hill = viewModel.getHillById(id).collectAsState(initial = null)

    val permissionRequired: String = stringResource(id = R.string.location_permission_required)
    val permissionRequiredFor: String =
        stringResource(id = R.string.location_permission_required_feature)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                viewModel.updateLocation(
                    LocationData(
                        hill.value!!.latitude.toDouble(),
                        hill.value!!.longitude.toDouble()
                    )
                )
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                val toastText = if (rationaleRequired) permissionRequired else permissionRequiredFor
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }
        }
    )

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.details_title)
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.padding(5.dp))
            if (hill.value == null) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                var hillDescription =
                    """
                        ${stringResource(id = R.string.hill_desc_county)}: ${hill.value!!.county}
                        ${stringResource(id = R.string.hill_desc_classification)}: ${hill.value!!.classification}
                        ${stringResource(id = R.string.hill_desc_height)}: ${hill.value!!.feet} (ft), ${hill.value!!.metres} (m)
                """.trimIndent()

                if (hill.value!!.climbed != null) {
                    hillDescription += "\n${stringResource(id = R.string.hill_walked_on)}: ${hill.value!!.climbed.toString()}"
                }


                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.hill_desc_name) + ": ${hill.value!!.name}",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(text = hillDescription, color = Color.Black)

                    Row {
                        if (hill.value!!.climbed == null) {
                            Button(
                                onClick = {
                                    openDatePickerDialog.value = !openDatePickerDialog.value
                                },
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(text = stringResource(id = R.string.mark_hill_walked))
                            }
                        }

                        Button(
                            onClick = {
                                if (locationUtils.hasLocationPermission(context)) {
                                    viewModel.updateLocation(
                                        LocationData(
                                            hill.value!!.latitude.toDouble(),
                                            hill.value!!.longitude.toDouble()
                                        )
                                    )
                                    navController.navigate(Screen.MapViewScreen.route) {
                                        this.launchSingleTop
                                    }
                                } else {
                                    requestPermissionLauncher.launch(
                                        arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.padding(2.dp)
                        ) {
                            Text(text = stringResource(id = R.string.view_on_map))
                        }
                    }
                }
            }
        }

        if (openDatePickerDialog.value) {
            // DatePickerDialog component with custom colors and button behaviors
            DatePickerDialog(
                onDismissRequest = {
                    openDatePickerDialog.value = false
                },
                confirmButton = {
                    // Confirm button with custom action and styling
                    TextButton(
                        onClick = {
                            // Action to set the selected date and close the dialog
                            openDatePickerDialog.value = false
                            val walkedDate =
                                datePickerState.selectedDateMillis?.convertMillisToDate()
                            viewModel.updateHill(hill.value!!.copy(climbed = walkedDate))
                        }
                    ) {
                        Text(stringResource(id = R.string.dialog_ok))
                    }
                },
                dismissButton = {
                    // Dismiss button to close the dialog without selecting a date
                    TextButton(
                        onClick = {
                            openDatePickerDialog.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.dialog_cancel))
                    }
                }
            ) {
                // The actual DatePicker component within the dialog
                DatePicker(state = datePickerState)
            }
        }
    }
}

fun Long.convertMillisToDate(): LocalDate {
    return Instant.ofEpochMilli(this@convertMillisToDate)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}