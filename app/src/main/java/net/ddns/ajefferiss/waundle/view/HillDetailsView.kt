package net.ddns.ajefferiss.waundle.view

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HillDetailsView(
    id: Long,
    viewModel: WaundleViewModel,
    navController: NavController,
    drawerState: DrawerState
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
    val openDatePickerDialog = remember { mutableStateOf(false) }
    val hill = viewModel.getHillById(id).collectAsState(initial = null)

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.details_title),
        showBottomBar = false
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
                        ${stringResource(id = R.string.classification)}: ${hill.value!!.classification}
                        ${stringResource(id = R.string.height)}: ${hill.value!!.feet} (ft), ${hill.value!!.metres} (m)
                """.trimIndent()

                if (hill.value!!.climbed != null) {
                    hillDescription += "\n${stringResource(id = R.string.hill_walked_on)}: ${hill.value!!.climbed.toString()}"
                }

                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = stringResource(id = R.string.hill_desc_name) + ": ${hill.value!!.name}",
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(text = hillDescription)

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
                                navController.navigate(Screen.MapViewScreen.route + "/${hill.value!!.id}") {
                                    this.launchSingleTop
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
                        Text(stringResource(id = R.string.ok))
                    }
                },
                dismissButton = {
                    // Dismiss button to close the dialog without selecting a date
                    TextButton(
                        onClick = {
                            openDatePickerDialog.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.cancel))
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