package io.getstream.compose.slack.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.BottomNavigation
import androidx.navigation.NavHostController
import io.getstream.compose.slack.R

/**
 * Represents the list of navigation endpoints for [BottomNavigation].
 *
 * @param route - string resource for [NavHostController] to navigate to a destination.
 * @param icon - drawable resource for showing an icon on the [BottomNavigation].
 * @param title - string resource for showing text for each destination on the[BottomNavigation].
 *
 * [Home] - Destination for home screen.
 * [DM] - Destination for dm's screen.
 * [Mentions] - Destination for mentions screen.
 * [Search] - Destination for search screen.
 * [Profile] - Destination for profile screen.
 */
sealed class NavigationItem(
    @StringRes
    val route: Int,
    @DrawableRes
    val icon: Int,
    @StringRes
    val title: Int
) {
    object Home : NavigationItem(R.string.route_home, R.drawable.ic_home, R.string.navigation_home)
    object DM : NavigationItem(R.string.route_dm, R.drawable.ic_chat_dm, R.string.navigation_dm)
    object Mentions : NavigationItem(
        R.string.route_mentions,
        R.drawable.ic_mentions,
        R.string.navigation_mentions
    )

    object Search :
        NavigationItem(R.string.route_search, R.drawable.ic_search, R.string.navigation_search)

    object Profile :
        NavigationItem(R.string.route_you, R.drawable.ic_person, R.string.navigation_profile)
}
