package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.LocationData

@Composable
fun HillMapView(location: LocationData, navController: NavController) {
    val snackBarHostState = remember { SnackbarHostState() }
    val hillLocation = LatLng(location.latitude, location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hillLocation, 10f)
    }

    var uiSettings by remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = true)) }
    var properties by remember { mutableStateOf(MapProperties(mapType = MapType.SATELLITE)) }

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
            modifier = Modifier.padding(it)
        ) {
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