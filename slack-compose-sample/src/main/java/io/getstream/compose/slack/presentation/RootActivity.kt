package io.getstream.compose.slack.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.compose.slack.R
import io.getstream.compose.slack.models.NavigationItem
import io.getstream.compose.slack.presentation.components.Navigation
import io.getstream.compose.slack.presentation.components.SlackDrawer
import kotlinx.coroutines.launch

class RootActivity : ComponentActivity() {
    val navigationItems = listOf(
        NavigationItem.Home,
        NavigationItem.DM,
        NavigationItem.Mentions,
        NavigationItem.Search,
        NavigationItem.Profile
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatTheme {
                val navController = rememberNavController()
                // create a scaffold state, set it to close by default
                val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
                val scope = rememberCoroutineScope()
                val openDrawer = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = stringResource(R.string.app_name),
                                    fontSize = 18.sp
                                )
                            },
                            backgroundColor = Color.White,
                            navigationIcon = {
                                IconButton(onClick = {
                                    openDrawer()
                                }) {
                                    Icon(Icons.Filled.Menu, "")
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = ChatTheme.colors.barsBackground
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            navigationItems.forEach { item ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painterResource(id = item.icon),
                                            contentDescription = item.title
                                        )
                                    },
                                    label = { Text(text = item.title) },
                                    selectedContentColor = Color.Black,
                                    unselectedContentColor = ChatTheme.colors.disabled,
                                    alwaysShowLabel = true,
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navigateToPage(navController, item)
                                    }
                                )
                            }
                        }
                    },
                    drawerBackgroundColor = colorResource(id = R.color.white),
                    drawerContent = {
                        SlackDrawer(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 24.dp, top = 48.dp)
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }
            }
        }
    }

    private fun navigateToPage(navController: NavHostController, item: NavigationItem) {
        navController.navigate(item.route) {
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
