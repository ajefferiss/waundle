package net.ddns.ajefferiss.waundle

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import net.ddns.ajefferiss.waundle.view.HillDetailsView
import net.ddns.ajefferiss.waundle.view.HillMapView
import net.ddns.ajefferiss.waundle.view.HomeView
import net.ddns.ajefferiss.waundle.view.SearchView
import net.ddns.ajefferiss.waundle.view.WaundleViewModel

@Composable
fun Navigation() {
    val viewModel: WaundleViewModel = viewModel()
    val navController: NavHostController = rememberNavController()
    val context: Context = LocalContext.current

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
            val id = if (entry.arguments != null) entry.arguments!!.getLong("id") else 0L
            HillDetailsView(
                id = id,
                viewModel = viewModel,
                navController = navController,
                context = context
            )
        }
        composable(Screen.SearchScreen.route) {
            SearchView(navController = navController, viewModel = viewModel)
        }
        composable(Screen.MapViewDialog.route) {
            viewModel.location.value?.let { locationIt ->
                HillMapView(location = locationIt, navController = navController)
            }
        }
    }
}