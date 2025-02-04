package net.ddns.ajefferiss.waundle.view

import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.data.CountriesMap
import net.ddns.ajefferiss.waundle.data.CountryCode
import net.ddns.ajefferiss.waundle.data.SCOTLAND
import net.ddns.ajefferiss.waundle.model.WaundleViewModel

@Composable
fun CountryCategoryListView(
    countryId: String?,
    navController: NavController,
    viewModel: WaundleViewModel,
    drawerState: DrawerState
) {
    val countryInfo = CountriesMap.getOrDefault(
        CountryCode.valueOf(countryId!!),
        SCOTLAND
    )

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(
            R.string.category_list_title,
            stringResource(countryInfo.nameId)
        ),
        showBottomBar = false
    ) {
        Text("TODO")
    }
}