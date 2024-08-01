package net.ddns.ajefferiss.waundle.view

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.PreferencesHelper.nearbyDistance
import net.ddns.ajefferiss.waundle.data.PreferencesHelper.sharedPreferences
import net.ddns.ajefferiss.waundle.data.UNSET_LOCATION
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.LocationUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NearbyHillsView(
    navController: NavController,
    drawerState: DrawerState,
    viewModel: WaundleViewModel,
    context: Context,
    locationUtils: LocationUtils
) {
    locationUtils.requestLocationUpdates(viewModel)
    val locationState by viewModel.location.collectAsState()
    val prefs = sharedPreferences(context)

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.nearby)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            if (locationState.latitude == UNSET_LOCATION || locationState.longitude == UNSET_LOCATION) {
                Text(stringResource(id = R.string.getting_current_location))
                CircularProgressIndicator(modifier = Modifier.width(64.dp))
            } else {
                val nearbyHills = viewModel.searchNearbyHills(prefs.nearbyDistance).collectAsState(
                    initial = listOf()
                )

                LazyColumn(
                    modifier = Modifier.fillMaxHeight()
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
                    items(nearbyHills.value, key = { hill -> hill.id }) { hill ->
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