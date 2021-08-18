package io.getstream.compose.slack.presentation.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.presentation.screens.HomeScreen

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
            HomeScreen()
        }
        composable(NavigationItem.Mentions.route) {
            HomeScreen()
        }
        composable(NavigationItem.Search.route) {
            HomeScreen()
        }
        composable(NavigationItem.Profile.route) {
            HomeScreen()
        }
    }
}
