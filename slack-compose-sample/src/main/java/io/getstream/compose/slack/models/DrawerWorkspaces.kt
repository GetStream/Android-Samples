package io.getstream.compose.slack.models

import androidx.annotation.DrawableRes
import io.getstream.compose.slack.R

sealed class DrawerWorkspaces(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
) {
    object Workspace1 : DrawerWorkspaces(
        title = "Workspace 1",
        description = "workspace1.slackc.com",
        image = R.drawable.dummy1
    )

    object Workspace2 : DrawerWorkspaces(
        title = "Workspace 2",
        description = "workspace2.slackc.com",
        image = R.drawable.dummy2
    )

    object Workspace3 : DrawerWorkspaces(
        "Workspace 3",
        description = "workspace3.slackc.com",
        image = R.drawable.dummy3
    )
}
