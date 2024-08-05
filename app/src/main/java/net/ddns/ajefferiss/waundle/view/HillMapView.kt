package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.WaundlePreferencesHelper

@Composable
fun HillMapView(
    id: Long,
    viewModel: WaundleViewModel,
    navController: NavController,
    drawerState: DrawerState,
    prefs: WaundlePreferencesHelper
) {
    val hill = viewModel.getHillById(id).collectAsState(initial = null)

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.details_title)
    ) {
        Column(modifier = Modifier.padding(it)) {
            if (hill.value == null || !prefs.isReady()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                val uiSettings by remember {
                    mutableStateOf(
                        MapUiSettings(
                            zoomControlsEnabled = true,
                            mapToolbarEnabled = true
                        )
                    )
                }
                val properties by remember {
                    mutableStateOf(
                        MapProperties(
                            mapType = prefs.getPrefs().mapType
                        )
                    )
                }

                val hillLocation = LatLng(
                    hill.value!!.latitude.toDouble(),
                    hill.value!!.longitude.toDouble()
                )
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(hillLocation, 10f)
                }

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings
                ) {
                    Marker(state = MarkerState(position = hillLocation))
                }
            }
        }
    }
}