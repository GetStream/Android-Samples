package io.getstream.compose.slack.models

import androidx.annotation.DrawableRes
import io.getstream.compose.slack.R

/**
 * Abstraction of workspace items
 */
interface DrawerWorkspaces {
    // Title to represent workspace name
    val title: String
    // Description text to represent workspace description or a URL
    val description: String
    // Image resource ID to represent workspace icon
    @get:DrawableRes
    val image: Int
}

class Workspace1 : DrawerWorkspaces {
    override val title: String = "Workspace 1"
    override val description: String = "workspace1.slackc.com"
    override val image: Int = R.drawable.dummy1

}

class Workspace2 : DrawerWorkspaces {
    override val title: String = "Workspace 2"
    override val description: String = "workspace2.slackc.com"
    override val image: Int = R.drawable.dummy2
}

class Workspace3 : DrawerWorkspaces {
    override val title: String = "Workspace 3"
    override val description: String = "workspace3.slackc.com"
    override val image: Int = R.drawable.dummy3
}
