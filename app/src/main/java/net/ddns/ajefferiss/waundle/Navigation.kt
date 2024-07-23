package net.ddns.ajefferiss.waundle

import android.content.Context
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.ddns.ajefferiss.waundle.menu.DrawerContent
import net.ddns.ajefferiss.waundle.menu.drawerMenuItems
import net.ddns.ajefferiss.waundle.view.HelpFeedbackView
import net.ddns.ajefferiss.waundle.view.HillDetailsView
import net.ddns.ajefferiss.waundle.view.HillMapView
import net.ddns.ajefferiss.waundle.view.HomeView
import net.ddns.ajefferiss.waundle.view.LiveTrackingView
import net.ddns.ajefferiss.waundle.view.NearbyHillsView
import net.ddns.ajefferiss.waundle.view.SearchView
import net.ddns.ajefferiss.waundle.view.SettingsView
import net.ddns.ajefferiss.waundle.view.WaundleViewModel

@Composable
fun Navigation() {
    val viewModel: WaundleViewModel = viewModel()
    val navController: NavHostController = rememberNavController()
    val context: Context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus = drawerMenuItems) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route.route)
                }
            }
        },
        scrimColor = MaterialTheme.colorScheme.surface
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(Screen.HomeScreen.route) {
                HomeView(
                    navController = navController,
                    viewModel = viewModel,
                    drawerState = drawerState
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
                    context = context,
                    drawerState = drawerState
                )
            }
            composable(Screen.SearchScreen.route) {
                SearchView(
                    navController = navController,
                    viewModel = viewModel,
                    drawerState = drawerState
                )
            }
            composable(Screen.MapViewScreen.route) {
                viewModel.location.value?.let { locationIt ->
                    HillMapView(
                        location = locationIt,
                        navController = navController,
                        drawerState = drawerState
                    )
                }
            }
            composable(Screen.SettingsScreen.route) {
                SettingsView(navController = navController, drawerState = drawerState)
            }
            composable(Screen.LiveTrackScreen.route) {
                LiveTrackingView(navController = navController, drawerState = drawerState)
            }
            composable(Screen.HelpFeedbackScreen.route) {
                HelpFeedbackView(navController = navController, drawerState = drawerState)
            }
            composable(Screen.NearByScreen.route) {
                NearbyHillsView(
                    navController = navController,
                    drawerState = drawerState,
                    viewModel = viewModel,
                    context = context
                )
            }
        }
    }
}