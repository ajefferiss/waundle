package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.data.CountryClassifications
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
    val countryClassifications = CountryClassifications[country]

    val searchResult = if (hillCategory == HillClassification.OtherHills) {
        val ignoreClassifications =
            countryClassifications!!.filter { it != HillClassification.OtherHills }
        viewModel.getCountryOtherHills(
            country.countryCode,
            ignoreClassifications
        ).collectAsState(initial = listOf())
    } else {
        viewModel.getHillsByCountryCategory(
            country.countryCode,
            "|" + hillCategory.code + "|"
        ).collectAsState(initial = listOf())
    }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = hillCategory.codeName
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
                item {
                    if (searchResult.value.isEmpty()) {
                        Text(stringResource(id = R.string.fetching_walked_count))
                    } else {
                        val walked = searchResult.value.count { h -> h.climbed != null }
                        val total = searchResult.value.count()
                        Text(
                            stringResource(
                                id = R.string.category_walked_count,
                                walked,
                                total
                            )
                        )
                    }
                }

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