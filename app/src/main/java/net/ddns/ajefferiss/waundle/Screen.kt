package net.ddns.ajefferiss.waundle

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object HillDetailsScreen : Screen("hill_detail_screen")
}