package net.ddns.ajefferiss.waundle

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object HillDetailsScreen : Screen("hill_detail_screen")
    data object SearchScreen : Screen("hill_search_screen")
    data object MapViewScreen : Screen("map_view_screen")
    data object SettingsScreen : Screen("settings_screen")
    data object LiveTrackScreen : Screen("live_tracking_screen")
    data object NearByScreen : Screen("nearby_screen")
    data object HelpFeedbackScreen : Screen("help_feedback_screen")
    data object PermissionRequestScreen : Screen("permission_request_screen")
    data object WalkedHillsScreen : Screen("walked_hills")
    data object CategoriesList : Screen("categories_list")
    data object HillsByCategory : Screen("hills_by_category")
}