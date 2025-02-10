package net.ddns.ajefferiss.waundle.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.MainActivity
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.LocationUtils


@Composable
fun PermissionRequest(
    navController: NavController,
    drawerState: DrawerState,
    locationUtils: LocationUtils,
    viewModel: WaundleViewModel
) {
    val locationPermissionRequired = stringResource(id = R.string.location_permission_required)
    val enableLocationPermission = stringResource(id = R.string.enable_location_permission)
    val permissionResult = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                permissionResult.value = true
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                val toastText = if (rationaleRequired) {
                    locationPermissionRequired
                } else {
                    enableLocationPermission
                }

                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }
        }
    )

    if (permissionResult.value) {
        navController.navigate(Screen.HomeScreen.route)
    }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.app_name)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    stringResource(id = R.string.location_permission_desc),
                    fontWeight = FontWeight.SemiBold
                )
                Text(stringResource(id = R.string.location_permission_rationale))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            requestPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }) {
                        Text(stringResource(id = R.string.request_location_permission))
                    }
                }
            }
        }
    }
}