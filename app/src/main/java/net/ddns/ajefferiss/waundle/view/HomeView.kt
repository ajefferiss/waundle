package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.util.LocationUtils

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WaundleViewModel,
    drawerState: DrawerState,
    locationUtils: LocationUtils
) {
    val walkedHills = viewModel.walkedHills.collectAsState(initial = listOf())
    val showProgressBar = viewModel.loading.collectAsState(initial = true)

    if (locationUtils.hasLocationPermission()) {
        locationUtils.requestLocationUpdates(viewModel)
    } else {
        navController.navigate(Screen.PermissionRequestScreen.route)
    }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.app_name)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (showProgressBar.value) {
                CircularProgressIndicator(modifier = Modifier.width(64.dp))
            }

            if (walkedHills.value.isEmpty()) {
                Row {
                    Text(
                        text = stringResource(id = R.string.no_walked_hills),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Row {
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
                                    text = stringResource(id = R.string.walked_hills_description),
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                        items(walkedHills.value, key = { hill -> hill.id }) { hill ->
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