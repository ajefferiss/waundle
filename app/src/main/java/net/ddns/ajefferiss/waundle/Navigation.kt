package net.ddns.ajefferiss.waundle

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.ddns.ajefferiss.waundle.util.LocationUtils
import net.ddns.ajefferiss.waundle.view.HillDetailsView
import net.ddns.ajefferiss.waundle.view.HomeView
import net.ddns.ajefferiss.waundle.view.SearchView
import net.ddns.ajefferiss.waundle.view.WaundleViewModel

@Composable
fun Navigation() {
    val viewModel: WaundleViewModel = viewModel()
    val navController: NavHostController = rememberNavController()
    val context: Context = LocalContext.current
    val locationUtils = LocationUtils(context)

    val permissionRequired: String = stringResource(id = R.string.location_permission_required)
    val permissionRequiredFor: String =
        stringResource(id = R.string.location_permission_required_feature)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] != true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] != true
            ) {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                val toastText = if (rationaleRequired) permissionRequired else permissionRequiredFor
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
            }
        }
    )

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
        composable(Screen.HomeScreen.route) {
            HomeView(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            route = Screen.HillDetailsScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            if (locationUtils.hasLocationPermission()) {
                val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
                HillDetailsView(id = id, viewModel = viewModel, navController = navController)
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
        composable(Screen.SearchScreen.route) {
            SearchView(navController = navController, viewModel = viewModel)
        }
    }
}