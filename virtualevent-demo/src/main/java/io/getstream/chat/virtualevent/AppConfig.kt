package io.getstream.chat.virtualevent

import io.getstream.chat.android.client.models.User

object AppConfig {
    const val API_KEY = "js6fh2y78g6w"

    val user = User(
        id = "samuel_urbanowicz",
        extraData = mutableMapOf(
            "name" to "Samuel",
            "image" to "https://firebasestorage.googleapis.com/v0/b/stream-chat-internal.appspot.com/o/users%2FJc.png?alt=media",
        ),
    )
    const val USER_TOKEN =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoic2FtdWVsX3VyYmFub3dpY3oifQ.Zsn_9f5LbvyV9F5jQ-_h7YvQNnNsnUQ6_IMRMemA334"

    const val LIVESTREAM_CHANNEL_1 = "livestream:esg_data_f22160f7-01fd-423f-b622-fe7060ec10d8"
    const val LIVESTREAM_CHANNEL_2 = "livestream:data-strategy_cfe253a5-785b-4c77-a5a0-ec63693d4e58"
}
