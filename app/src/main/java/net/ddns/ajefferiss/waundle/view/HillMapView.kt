package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import net.ddns.ajefferiss.waundle.util.GOOGLE_MAP_TYPES_MAP
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.mapType
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.sharedPreferences

@Composable
fun HillMapView(location: LocationData, navController: NavController, drawerState: DrawerState) {
    val prefs = sharedPreferences(LocalContext.current)
    val hillLocation = LatLng(location.latitude, location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hillLocation, 10f)
    }

    var uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = true,
                mapToolbarEnabled = true
            )
        )
    }
    var properties by remember {
        mutableStateOf(
            MapProperties(
                mapType = GOOGLE_MAP_TYPES_MAP.getOrDefault(prefs.mapType, MapType.SATELLITE)
            )
        )
    }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.details_title)
    ) {
        Column(modifier = Modifier.padding(it)) {
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