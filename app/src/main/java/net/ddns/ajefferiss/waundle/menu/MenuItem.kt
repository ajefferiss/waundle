package net.ddns.ajefferiss.waundle.menu

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import net.ddns.ajefferiss.waundle.Screen

data class NavItem(val icon: ImageVector, @StringRes val title: Int, val route: Screen)