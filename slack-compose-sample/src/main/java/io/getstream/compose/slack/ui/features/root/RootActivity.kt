package io.getstream.compose.slack.ui.features.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.models.Workspace1
import io.getstream.compose.slack.models.Workspace2
import io.getstream.compose.slack.models.Workspace3
import io.getstream.compose.slack.shapes
import io.getstream.compose.slack.ui.features.direct.DirectMessagesScreen
import io.getstream.compose.slack.ui.features.home.HomeScreen
import io.getstream.compose.slack.ui.features.mentions.MentionsScreen
import io.getstream.compose.slack.ui.features.messaging.MessagingActivity
import io.getstream.compose.slack.ui.features.profile.ProfileScreen
import io.getstream.compose.slack.ui.features.search.SearchScreen
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RootActivity : ComponentActivity() {
    private val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.DM,
        NavigationItem.Mentions,
        NavigationItem.Search,
        NavigationItem.Profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme(shapes = shapes()) {
                RootContent()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    internal fun RootContent(
        modifier: Modifier = Modifier
    ) {
        val navController = rememberNavController()
        // create a scaffold state, set it to close by default
        val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
        val scope = rememberCoroutineScope()
        var showDrawerState by remember { mutableStateOf(true) }
        var drawerTitle by remember { mutableStateOf("Home") }
        val openDrawer: () -> Job = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }

        Scaffold(
            modifier = modifier,
            topBar = {
                RootHeader(
                    title = drawerTitle,
                    showDrawerState = showDrawerState
                ) {
                    openDrawer()
                }
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                RootBottomNavigation(
                    navController = navController
                ) { item, title ->
                    showDrawerState = when (item) {
                        NavigationItem.Home -> true
                        else -> false
                    }
                    drawerTitle = title
                    navigateToPage(navController, item)
                }
            },
            drawerBackgroundColor = colorResource(id = R.color.white),
            drawerContent = {
                SlackDrawerContent(
                    workspaces = listOf(
                        Workspace1(),
                        Workspace2(),
                        Workspace3()
                    )
                )
            },
            drawerGesturesEnabled = showDrawerState,
        ) { paddingValues ->
            SlackNavigation(
                modifier = Modifier.padding(paddingValues),
                navController = navController
            ) { channel ->
                startActivity(
                    MessagingActivity.getIntent(
                        channelId = channel.cid,
                        context = this@RootActivity
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Preview
    @Composable
    fun RootContentPreview() {
        ChatTheme(shapes = shapes()) {
            RootContent()
        }
    }

    private fun navigateToPage(
        navController: NavHostController,
        item: NavigationItem
    ) {
        // Get Route string resource
        val route = getString(item.route)

        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            navController.graph.startDestinationRoute?.let { route ->
                popUpTo(route) {
                    saveState = true
                }
            }
            // Avoid multiple copies of the same destination when
            // re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }
    }

    /**
     * Composable that represents the top header for the root activity.
     *
     * @param modifier - Modifier for styling.
     * @param showDrawerState - Boolean state to decide if drawer button is visible.
     * @param onDrawerOpen - Handler for when drawer button is clicked.
     * @param title - Title to show on the top bar.
     * */
    @Composable
    internal fun RootHeader(
        title: String,
        showDrawerState: Boolean,
        modifier: Modifier = Modifier,
        onDrawerOpen: () -> Job,
    ) {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = title,
                    style = ChatTheme.typography.title3Bold,
                    color = ChatTheme.colors.textHighEmphasis
                )
            },
            backgroundColor = ChatTheme.colors.barsBackground,
            navigationIcon = {
                if (showDrawerState) {
                    IconButton(
                        onClick = {
                            onDrawerOpen()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            tint = ChatTheme.colors.textHighEmphasis,
                            contentDescription = getString(R.string.accessibility_drawer)
                        )
                    }
                }
            }
        )
    }

    /**
     * Composable that represents the bottom navigation for the root activity.
     *
     * @param navController - instance of [NavHostController] to handle  selection of navigation route.
     * @param modifier - Modifier for styling.
     * @param onNavigationClick - Handler for when a navigation item is clicked.
     * */
    @Composable
    internal fun RootBottomNavigation(
        navController: NavHostController,
        modifier: Modifier = Modifier,
        onNavigationClick: (NavigationItem, String) -> Unit
    ) {
        BottomNavigation(
            modifier = modifier,
            backgroundColor = ChatTheme.colors.barsBackground
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            navigationItems.forEach { item ->
                val title = stringResource(id = item.title)

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = title
                        )
                    },
                    label = { Text(text = title) },
                    selectedContentColor = ChatTheme.colors.textHighEmphasis,
                    unselectedContentColor = ChatTheme.colors.disabled,
                    alwaysShowLabel = true,
                    selected = currentRoute == stringResource(id = item.route),
                    onClick = {
                        onNavigationClick(item, title)
                    }
                )
            }
        }
    }

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
    internal fun SlackNavigation(
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
                ProfileScreen(
                    isOnline = true
                )
            }
        }
    }
}
