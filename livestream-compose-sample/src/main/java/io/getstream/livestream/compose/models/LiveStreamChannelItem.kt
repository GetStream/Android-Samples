package io.getstream.livestream.compose.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LiveStreamChannelItem(
    val channelId: String,
    val channelName: String,
    @StringRes
    val channelDescription: Int,
    @DrawableRes
    val channelArt: Int
)
