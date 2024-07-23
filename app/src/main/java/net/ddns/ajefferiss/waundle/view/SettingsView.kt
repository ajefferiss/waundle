package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.nearbyDistance
import net.ddns.ajefferiss.waundle.util.PreferencesHelper.sharedPreferences

@Composable
fun SettingsView(navController: NavController, drawerState: DrawerState) {
    val prefs = sharedPreferences(LocalContext.current)
    var nearbyDistance by remember { mutableIntStateOf(prefs.nearbyDistance) }

    WaundleScaffold(
        navController = navController,
        drawerState = drawerState,
        title = stringResource(id = R.string.settings)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = stringResource(id = R.string.nearby_hill_setting))
                OutlinedTextField(
                    value = nearbyDistance.toString(),
                    onValueChange = { nb ->
                        if (nb.isNotEmpty() && nb.matches(Regex("^\\d+\$"))) {
                            prefs.nearbyDistance = nb.toInt()
                            nearbyDistance = nb.toInt()
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}