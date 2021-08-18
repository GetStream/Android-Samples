package io.getstream.compose.slack.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.presentation.screens.DMScreen
import io.getstream.compose.slack.presentation.screens.HomeScreen
import io.getstream.compose.slack.presentation.screens.MentionsScreen
import io.getstream.compose.slack.presentation.screens.ProfileScreen
import io.getstream.compose.slack.presentation.screens.SearchScreen

/**
 * A view component to draw a bottom navigation bar,
 * items clicked will navigate to corresponding screen.
 *
 * @param navController - [NavHostController] instance for deciding when a particular screen/route
 * is loaded , corresponding composable screen content to be drawn
 */
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Home.route) {
        composable(NavigationItem.Home.route) {
            HomeScreen()
        }
        composable(NavigationItem.DM.route) {
            DMScreen()
        }
        composable(NavigationItem.Mentions.route) {
            MentionsScreen()
        }
        composable(NavigationItem.Search.route) {
            SearchScreen()
        }
        composable(NavigationItem.Profile.route) {
            ProfileScreen()
        }
    }
}
