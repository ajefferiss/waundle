package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R

@Composable
fun SettingsView(navController: NavController, drawerState: DrawerState) {
    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.settings)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("TODO")
        }
    }
}