package io.getstream.compose.slack.ui.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.getstream.chat.android.client.models.Channel
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.ui.features.direct.DMScreen
import io.getstream.compose.slack.ui.features.home.HomeScreen
import io.getstream.compose.slack.ui.features.mentions.MentionsScreen
import io.getstream.compose.slack.ui.features.profile.ProfileScreen
import io.getstream.compose.slack.ui.features.search.SearchScreen

/**
 * A view component to draw a bottom navigation bar,
 * items clicked will navigate to corresponding screen.
 *
 * @param modifier - Styling the NavHost root component.
 * @param navController - [NavHostController] instance for deciding when a particular screen/route
 * is loaded , corresponding composable screen content to be drawn.
 * @param onChannelClick - Handler for Channel item clicks.
 */
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onChannelClick: (Channel) -> Unit
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavigationItem.Home.route
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen { channel ->
                onChannelClick(channel)
            }
        }
        composable(NavigationItem.DM.route) {
            DMScreen { channel ->
                onChannelClick(channel)
            }
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
