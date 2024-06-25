package net.ddns.ajefferiss.waundle

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object HillDetailsScreen : Screen("hill_detail_screen")
    data object SearchScreen : Screen("hill_search_screen")
}