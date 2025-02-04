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
import net.ddns.ajefferiss.waundle.data.CountryDescription
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

    val countries = listOf(
        CountryDescription(
            stringResource(R.string.scotland),
            stringResource(R.string.scotland_description),
            R.drawable.scotland_hill,
        ),
        CountryDescription(
            stringResource(R.string.england),
            stringResource(R.string.england_description),
            R.drawable.english_hill
        ),
        CountryDescription(
            stringResource(R.string.ireland),
            stringResource(R.string.ireland_description),
            R.drawable.irish_hill
        ),
        CountryDescription(
            stringResource(R.string.wales),
            stringResource(R.string.wales_description),
            R.drawable.welsh_hill
        ),
        CountryDescription(
            stringResource(R.string.isle_of_man),
            stringResource(R.string.isle_of_man_description),
            R.drawable.isle_of_man_hill
        )
    )

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
                items(countries, key = { country -> country.name.hashCode() }) { country ->
                    CountryItem(
                        country
                    )
//                    HillItem(
//                        hill = hill,
//                        onClick = {
//                            navController.navigate(Screen.HillDetailsScreen.route + "/${hill.id}")
//                        }
//                    )
                }
            }
        }
    }
}