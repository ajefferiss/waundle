package net.ddns.ajefferiss.waundle

import android.content.Context
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
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
import net.ddns.ajefferiss.waundle.menu.NavItem
import net.ddns.ajefferiss.waundle.model.WaundleViewModel
import net.ddns.ajefferiss.waundle.util.LocationUtils
import net.ddns.ajefferiss.waundle.util.WaundlePreferencesHelper
import net.ddns.ajefferiss.waundle.view.CountryCategoryListView
import net.ddns.ajefferiss.waundle.view.HelpFeedbackView
import net.ddns.ajefferiss.waundle.view.HillDetailsView
import net.ddns.ajefferiss.waundle.view.HillsByCategoryView
import net.ddns.ajefferiss.waundle.view.HomeView
import net.ddns.ajefferiss.waundle.view.LiveTrackingView
import net.ddns.ajefferiss.waundle.view.NearbyHillsView
import net.ddns.ajefferiss.waundle.view.PermissionRequest
import net.ddns.ajefferiss.waundle.view.SearchView
import net.ddns.ajefferiss.waundle.view.SettingsView
import net.ddns.ajefferiss.waundle.view.WalkedHillsView

@Composable
fun Navigation() {
    val viewModel: WaundleViewModel = viewModel()
    val navController: NavHostController = rememberNavController()
    val context: Context = LocalContext.current
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val locationUtils = LocationUtils(context)
    val waundlePrefs = WaundlePreferencesHelper(context)

    val drawerMenuItems = listOf(
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_home_icon),
            R.string.home,
            Screen.HomeScreen
        ),
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_walked_icon),
            R.string.walked_hills,
            Screen.WalkedHillsScreen
        ),
//        NavItem(
//            ImageVector.vectorResource(id = R.drawable.ic_map_icon),
//            R.string.live_track,
//            Screen.LiveTrackScreen
//        ),
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_nearby_icon),
            R.string.nearby_hills,
            Screen.NearByScreen
        ),
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_settings_icon),
            R.string.settings,
            Screen.SettingsScreen
        ),
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_info_icon),
            R.string.help_feedback,
            Screen.HelpFeedbackScreen
        )
    )

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
        }
    ) {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(Screen.HomeScreen.route) {
                HomeView(
                    navController = navController,
                    viewModel = viewModel,
                    drawerState = drawerState,
                    locationUtils = locationUtils
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
                val id = entry.arguments?.getLong("id") ?: 0L
                HillDetailsView(
                    id = id,
                    viewModel = viewModel,
                    navController = navController,
                    drawerState = drawerState,
                    context = context
                )
            }
            composable(Screen.SearchScreen.route) {
                SearchView(
                    navController = navController,
                    viewModel = viewModel,
                    drawerState = drawerState
                )
            }
            composable(Screen.SettingsScreen.route) {
                SettingsView(
                    navController = navController,
                    drawerState = drawerState,
                    viewModel = viewModel,
                    prefs = waundlePrefs
                )
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
                    locationUtils = locationUtils,
                    prefs = waundlePrefs
                )
            }
            composable(Screen.PermissionRequestScreen.route) {
                PermissionRequest(
                    navController = navController,
                    drawerState = drawerState,
                    locationUtils = locationUtils,
                    viewModel = viewModel
                )
            }
            composable(Screen.WalkedHillsScreen.route) {
                WalkedHillsView(
                    navController = navController,
                    viewModel = viewModel,
                    drawerState = drawerState
                )
            }
            composable(
                route = Screen.CategoriesListScreen.route + "/{countryCode}",
                arguments = listOf(
                    navArgument("countryCode") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    }
                )
            ) { entry ->
                val id = entry.arguments?.getString("countryCode") ?: ""
                CountryCategoryListView(
                    countryId = id,
                    navController = navController,
                    drawerState = drawerState,
                )
            }
            composable(
                route = Screen.HillsByCategoryScreen.route + "/{hillCategory}/{country}",
                arguments = listOf(
                    navArgument("hillCategory") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    },
                    navArgument("country") {
                        type = NavType.StringType
                        defaultValue = ""
                        nullable = false
                    }
                )
            ) { entry ->
                val code = entry.arguments?.getString("hillCategory") ?: ""
                val countryCode = entry.arguments?.getString("country") ?: ""
                HillsByCategoryView(
                    categoryId = code,
                    countryCode = countryCode,
                    navController = navController,
                    drawerState = drawerState,
                    viewModel = viewModel
                )
            }
        }
    }
}