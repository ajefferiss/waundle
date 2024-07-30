package net.ddns.ajefferiss.waundle.menu

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import net.ddns.ajefferiss.waundle.R
import net.ddns.ajefferiss.waundle.Screen

data class NavItem(val icon: ImageVector, @StringRes val title: Int, val route: Screen)


val bottomMenuItems = listOf(
    NavItem(Icons.Filled.Home, R.string.home, Screen.HomeScreen),

    )