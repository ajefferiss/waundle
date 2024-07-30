package net.ddns.ajefferiss.waundle.view

import android.content.Context
import android.location.Location
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
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
    val allHills = viewModel.getAllHills().collectAsState(
        initial = listOf()
    )

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
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(id = R.string.nearby_hill_desc),
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

                        if (currentLocation.distanceTo(hillLocation) <= prefs.nearbyDistance) {
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