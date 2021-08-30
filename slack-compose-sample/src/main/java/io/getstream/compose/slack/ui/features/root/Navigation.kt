package io.getstream.compose.slack.ui.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.getstream.chat.android.client.models.Channel
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.ui.features.direct.DirectMessagesScreen
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
    val homeRoute = stringResource(id = NavigationItem.Home.route)
    val dmsRoute = stringResource(id = NavigationItem.DM.route)
    val mentionsRoute = stringResource(id = NavigationItem.Mentions.route)
    val searchRoute = stringResource(id = NavigationItem.Search.route)
    val profileRoute = stringResource(id = NavigationItem.Profile.route)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = stringResource(id = NavigationItem.Home.route)
    ) {
        composable(homeRoute) {
            HomeScreen { channel ->
                onChannelClick(channel)
            }
        }
        composable(dmsRoute) {
            DirectMessagesScreen { channel ->
                onChannelClick(channel)
            }
        }
        composable(mentionsRoute) {
            MentionsScreen()
        }
        composable(searchRoute) {
            SearchScreen()
        }
        composable(profileRoute) {
            ProfileScreen()
        }
    }
}
