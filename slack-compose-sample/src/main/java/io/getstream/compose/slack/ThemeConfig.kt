package io.getstream.compose.slack

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.StreamColors
import io.getstream.chat.android.compose.ui.theme.StreamShapes

/**
 * This class can be added with more Theme customizations for ChatTheme, such as [StreamShapes]
 */
@Composable
fun shapes(): StreamShapes {
    return StreamShapes(
        avatar = RoundedCornerShape(8.dp),
        myMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp),
        otherMessageBubble = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        inputField = RoundedCornerShape(0.dp),
        attachment = RoundedCornerShape(8.dp),
        imageThumbnail = RoundedCornerShape(8.dp),
        bottomSheet = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )
}

@Composable
public fun lightColors(): StreamColors = StreamColors(
    textHighEmphasis = colorResource(R.color.stream_compose_text_high_emphasis),
    textLowEmphasis = colorResource(R.color.stream_compose_text_low_emphasis),
    disabled = colorResource(R.color.stream_compose_disabled),
    borders = Color.Transparent,
    inputBackground = colorResource(R.color.stream_compose_input_background),
    appBackground = colorResource(R.color.stream_compose_app_background),
    barsBackground = colorResource(R.color.stream_compose_bars_background),
    linkBackground = colorResource(R.color.stream_compose_link_background),
    overlay = colorResource(R.color.stream_compose_overlay),
    primaryAccent = colorResource(id = R.color.stream_compose_primary_accent),
    errorAccent = colorResource(R.color.stream_compose_error_accent),
    infoAccent = colorResource(R.color.stream_compose_info_accent),
)

@Composable
public fun darkColors(): StreamColors = StreamColors(
    textHighEmphasis = colorResource(R.color.stream_compose_text_high_emphasis_dark),
    textLowEmphasis = colorResource(R.color.stream_compose_text_low_emphasis_dark),
    disabled = colorResource(R.color.stream_compose_disabled_dark),
    borders = Color.Transparent,
    inputBackground = colorResource(R.color.stream_compose_input_background_dark),
    appBackground = colorResource(R.color.stream_compose_app_background_dark),
    barsBackground = colorResource(R.color.stream_compose_bars_background_dark),
    linkBackground = colorResource(R.color.stream_compose_link_background_dark),
    overlay = colorResource(R.color.stream_compose_overlay_dark),
    primaryAccent = colorResource(id = R.color.stream_compose_primary_accent_dark),
    errorAccent = colorResource(R.color.stream_compose_error_accent_dark),
    infoAccent = colorResource(R.color.stream_compose_info_accent_dark),
)
