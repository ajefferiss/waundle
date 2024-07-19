package net.ddns.ajefferiss.waundle.view

import android.Manifest
import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.MainActivity
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.util.LocationUtils
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.nearbyDistance
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.sharedPreferences

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NearbyHillsView(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: WaundleViewModel,
    context: Context
) {
    val prefs = sharedPreferences(context)
    val locationUtils = LocationUtils(context)
    val allHills = viewModel.getAllHills().collectAsState(
        initial = listOf()
    )
    val permissionRequired: String = stringResource(id = R.string.location_permission_required)
    val permissionRequiredFor: String =
        stringResource(id = R.string.location_permission_required_feature)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                locationUtils.requestLocationUpdates(viewModel)
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

    LaunchedEffect(true) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdates(viewModel)
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.nearby)
    ) {
        if (viewModel.location.value != null) {
            val currentLocation = Location("point a")
            currentLocation.latitude = viewModel.location.value!!.latitude
            currentLocation.longitude = viewModel.location.value!!.longitude

            Column(
                modifier = Modifier.padding(it)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(it)
                ) {
                    stickyHeader {
                        Column(
                            modifier = Modifier
                                .height(40.dp)
                                .fillMaxWidth()
                        ) {
                            WaundleTextField(
                                text = stringResource(id = R.string.walked_hills_description),
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    items(allHills.value, key = { hill -> hill.id }) { hill ->
                        val hillLocation = Location("point b")
                        hillLocation.latitude = hill.latitude.toDouble()
                        hillLocation.longitude = hill.longitude.toDouble()

                        if (currentLocation.distanceTo(hillLocation) / 1000 <= prefs.nearbyDistance) {
                            HillItem(
                                hill = hill,
                                onClick = {
                                    navController.navigate(Screen.HillDetailsScreen.route + "/${hill.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}