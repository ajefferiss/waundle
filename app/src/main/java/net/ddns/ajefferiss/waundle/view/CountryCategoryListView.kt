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
import net.ddns.ajefferiss.waundle.data.CountriesMap
import net.ddns.ajefferiss.waundle.data.CountryClassifications
import net.ddns.ajefferiss.waundle.data.CountryCode
import net.ddns.ajefferiss.waundle.data.SCOTLAND

@Composable
fun CountryCategoryListView(
    countryId: String?,
    navController: NavController,
    drawerState: DrawerState
) {
    val countryDetail = CountryCode.valueOf(countryId!!)
    val countryInfo = CountriesMap.getOrDefault(countryDetail, SCOTLAND)
    val hillCategories = CountryClassifications.getOrDefault(countryInfo.countryCode, listOf())

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(
            R.string.category_list_title,
            stringResource(countryInfo.nameId)
        ),
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
                items(
                    hillCategories,
                    key = { hillCategory -> hillCategory.hashCode() }
                ) { hillCategory ->
                    ImageCard(
                        description = hillCategory.name,
                        imageId = hillCategory.imageId ?: countryInfo.image,
                        onClick = {
                            navController.navigate(Screen.HillsByCategory.route + "/${hillCategory.code}/${countryDetail.countryCode}")
                        }
                    )
                }
            }
        }
    }
}