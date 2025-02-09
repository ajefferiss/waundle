package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.CountryCode
import net.ddns.ajefferiss.waundle.data.HillClassification
import net.ddns.ajefferiss.waundle.model.WaundleViewModel

@Composable
fun HillsByCategoryView(
    categoryId: String,
    countryCode: String,
    navController: NavController,
    drawerState: DrawerState,
    viewModel: WaundleViewModel
) {
    val hillCategory = HillClassification.findByCode(categoryId) ?: HillClassification.OtherHills
    val country = CountryCode.valueOf(countryCode)
    val searchResult = viewModel.getHillsByCountryCategory(
        country.countryCode,
        hillCategory.code
    ).collectAsState(initial = listOf())

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = hillCategory.codeName,
        showBottomBar = false
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
                items(searchResult.value, key = { hill -> hill.hillId }) { hill ->
                    HillItem(
                        hill = hill,
                        onClick = {
                            navController.navigate(Screen.HillDetailsScreen.route + "/${hill.hillId}")
                        }
                    )
                }
            }
        }
    }
}