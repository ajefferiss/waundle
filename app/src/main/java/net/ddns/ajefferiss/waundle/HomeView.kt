package net.ddns.ajefferiss.waundle

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import net.ddns.ajefferiss.waundle.view.WaundleViewModel

@Composable
fun HomeView(navController: NavController, viewModel: WaundleViewModel) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBarView(
                title = stringResource(id = R.string.app_name)
            )
        }
    ) {

    }
}