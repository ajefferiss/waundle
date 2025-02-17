package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.menu.NavItem

@Composable
fun WaundleScaffold(
    navController: NavController,
    drawerState: DrawerState,
    title: String,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    showBottomBar: Boolean = false,
    content: @Composable() (PaddingValues) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val bottomNavigationItems = listOf(
        NavItem(
            ImageVector.vectorResource(id = R.drawable.ic_home_icon),
            R.string.home,
            Screen.HomeScreen
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
            ImageVector.vectorResource(id = R.drawable.ic_walked_icon),
            R.string.walked_hills,
            Screen.WalkedHillsScreen
        )
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppBarView(
                title = title,
                navController = navController,
                drawerState = drawerState
            )
        },
        bottomBar = {
            if (showBottomBar) {
                AppBottomBar(
                    destinations = bottomNavigationItems,
                    currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                    onNavigateToDestination = {
                        navController.navigate(it.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        content = content,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition
    )
}