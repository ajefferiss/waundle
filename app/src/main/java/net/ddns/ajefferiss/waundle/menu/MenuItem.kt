package net.ddns.ajefferiss.waundle.menu

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen

data class NavItem(val icon: ImageVector, @StringRes val title: Int, val route: Screen)

val drawerMenuItems = listOf(
    NavItem(Icons.Filled.Home, R.string.home, Screen.HomeScreen),
    NavItem(Icons.Filled.Place, R.string.live_track, Screen.LiveTrackScreen),
    NavItem(Icons.Filled.LocationOn, R.string.nearby, Screen.NearByScreen),
    NavItem(Icons.Filled.Settings, R.string.settings, Screen.SettingsScreen),
    NavItem(Icons.Filled.Info, R.string.help_feedback, Screen.HelpFeedbackScreen)
)