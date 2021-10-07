package io.getstream.slack.compose.model

import androidx.annotation.DrawableRes

/**
 * A model that represents a Slack workspace.
 *
 * @param title The name of the workspace.
 * @param logo The logo of the workspace.
 */
data class Workspace(
    val title: String,
    @DrawableRes
    val logo: Int
)
