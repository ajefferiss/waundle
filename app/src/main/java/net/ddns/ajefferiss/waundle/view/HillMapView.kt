package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.LocationData

@Composable
fun HillMapView(location: LocationData, onCloseClicked: () -> Unit) {
    val hillLocation = LatLng(location.latitude, location.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hillLocation, 10f)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(state = MarkerState(position = hillLocation))
        }

        Button(onClick = { onCloseClicked() }) {
            WaundleTextField(text = stringResource(id = R.string.close))
        }
    }
}