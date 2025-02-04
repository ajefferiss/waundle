package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.CountriesList
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.LocationUtils

@Composable
fun HomeView(
    navController: NavController,
    viewModel: WaundleViewModel,
    drawerState: DrawerState,
    locationUtils: LocationUtils
) {
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    CountriesList,
                    key = { country -> country.countryCode.hashCode() }
                ) { country ->
                    CountryItem(
                        country,
                        onClick = {
                            navController.navigate(Screen.CategoriesList.route + "/${country.countryCode}")
                        }
                    )
                }
            }
        }
    }
}