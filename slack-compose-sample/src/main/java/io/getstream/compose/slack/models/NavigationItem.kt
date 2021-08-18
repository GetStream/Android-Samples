package io.getstream.compose.slack.models

import io.getstream.compose.slack.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object DM : NavigationItem("music", R.drawable.ic_chat_dm, "DMs")
    object Mentions : NavigationItem("movies", R.drawable.ic_mentions, "Mentions")
    object Search : NavigationItem("books", R.drawable.ic_search, "Search")
    object Profile : NavigationItem("profile", R.drawable.ic_person, "You")
}
