package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import net.ddns.ajefferiss.waundle.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HillDetailsView(id: Long, viewModel: WaundleViewModel, navController: NavController) {
    val cameraZoom = 10f
    val snackBarHostState = remember { SnackbarHostState() }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    val openDatePickerDialog = remember { mutableStateOf(false) }

    var hill = viewModel.getHillById(id).collectAsState(initial = null)

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.details_title),
                navController = navController
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            if (hill.value == null) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                Text(
                    text = stringResource(id = R.string.hill_desc_name) + ": ${hill.value!!.name}",
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = stringResource(id = R.string.hill_desc_county) + ": ${hill.value!!.county}")
                Text(text = stringResource(id = R.string.hill_desc_classification) + ": ${hill.value!!.classification}")
                Text(text = stringResource(id = R.string.hill_desc_height) + ": ${hill.value!!.feet} (ft), ${hill.value!!.metres} (m)")
                if (hill.value!!.climbed != null) {
                    Text(text = stringResource(id = R.string.hill_walked_on) + " " + hill.value!!.climbed.toString())
                } else {
                    Button(
                        onClick = {
                            openDatePickerDialog.value = !openDatePickerDialog.value
                        }
                    ) {
                        Text(text = stringResource(id = R.string.mark_hill_walked))
                    }
                }

                GoogleMap(
                    modifier = Modifier.padding(top = 16.dp),
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(
                            LatLng(
                                hill.value!!.latitude.toDouble(),
                                hill.value!!.longitude.toDouble()
                            ),
                            cameraZoom
                        )
                    )
                )
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
                        Text("OK")
                    }
                },
                dismissButton = {
                    // Dismiss button to close the dialog without selecting a date
                    TextButton(
                        onClick = {
                            openDatePickerDialog.value = false
                        }
                    ) {
                        Text("CANCEL")
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