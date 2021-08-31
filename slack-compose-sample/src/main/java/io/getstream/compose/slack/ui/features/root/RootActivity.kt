package io.getstream.compose.slack.ui.features.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerValue
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.getstream.chat.android.client.models.name
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.models.DrawerWorkspaces
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.models.Workspace1
import io.getstream.compose.slack.models.Workspace2
import io.getstream.compose.slack.models.Workspace3
import io.getstream.compose.slack.shapes
import io.getstream.compose.slack.ui.features.messaging.MessagingActivity
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
        var fabVisibilityState by remember { mutableStateOf(true) }
        val size = animateDpAsState(if (fabVisibilityState) 56.dp else 0.dp)
        var showDrawerState by remember { mutableStateOf(true) }
        var drawerTitle by remember { mutableStateOf("Home") }
        val openDrawer = {
            scope.launch {
                scaffoldState.drawerState.open()
            }
        }

        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = drawerTitle,
                            style = ChatTheme.typography.title3Bold,
                            color = ChatTheme.colors.textHighEmphasis
                        )
                    },
                    backgroundColor = ChatTheme.colors.barsBackground,
                    navigationIcon = {
                        if (showDrawerState) {
                            IconButton(
                                onClick = {
                                    openDrawer()
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
            },
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigation(
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
                                fabVisibilityState = when (item) {
                                    NavigationItem.DM -> true
                                    NavigationItem.Home -> true
                                    NavigationItem.Mentions -> true
                                    else -> false
                                }
                                showDrawerState = when (item) {
                                    NavigationItem.Home -> true
                                    else -> false
                                }
                                drawerTitle = title
                                navigateToPage(navController, item)
                            }
                        )
                    }
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
            Navigation(
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
}
