package net.ddns.ajefferiss.waundle.view

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import net.ddns.ajefferiss.waundle.Screen
import net.ddns.ajefferiss.waundle.menu.NavItem

@Composable
fun AppBottomBar(
    destinations: List<NavItem>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (screen: Screen) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom)
            )
            .height(70.dp),
    ) {
        destinations.forEach { destination ->
            var selected =
                currentDestination?.hierarchy?.any { it.route == destination.route.route }
            if (selected == null) {
                selected = false
            }

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination.route) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        modifier = Modifier.size(16.dp),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(id = destination.title))
                }
            )
        }
    }
}