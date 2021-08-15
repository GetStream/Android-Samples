package io.getstream.livestream.compose

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.ui.theme.StreamColors
import io.getstream.chat.android.compose.ui.theme.StreamShapes

@Composable
fun lightColorPalette(): StreamColors {
    return StreamColors(
        textHighEmphasis = colorResource(R.color.light_text_high_emphasis),
        textLowEmphasis = colorResource(R.color.light_text_low_emphasis),
        disabled = colorResource(R.color.light_disabled),
        borders = colorResource(R.color.light_borders),
        inputBackground = colorResource(R.color.light_input_background),
        appBackground = colorResource(R.color.light_app_background),
        barsBackground = colorResource(R.color.light_bars_background),
        linkBackground = colorResource(R.color.light_link_background),
        overlay = colorResource(R.color.light_overlay),
        primaryAccent = colorResource(id = R.color.light_primary_accent),
        errorAccent = colorResource(R.color.light_error_accent),
        infoAccent = colorResource(R.color.light_info_accent),
    )
}

@Composable
fun darkColorPalette(): StreamColors {
    return StreamColors(
        textHighEmphasis = colorResource(R.color.dark_text_high_emphasis),
        textLowEmphasis = colorResource(R.color.dark_text_low_emphasis),
        disabled = colorResource(R.color.dark_disabled),
        borders = colorResource(R.color.dark_borders),
        inputBackground = colorResource(R.color.dark_input_background),
        appBackground = colorResource(R.color.dark_app_background),
        barsBackground = colorResource(R.color.dark_bars_background),
        linkBackground = colorResource(R.color.dark_link_background),
        overlay = colorResource(R.color.dark_overlay),
        primaryAccent = colorResource(id = R.color.dark_primary_accent),
        errorAccent = colorResource(R.color.dark_error_accent),
        infoAccent = colorResource(R.color.dark_info_accent),
    )
}

@Composable
fun shapes(): StreamShapes {
    return StreamShapes(
        avatar = CircleShape,
        myMessageBubble = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp),
        otherMessageBubble = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomEnd = 16.dp
        ),
        inputField = RoundedCornerShape(8.dp),
        attachment = RoundedCornerShape(16.dp)
    )
}
