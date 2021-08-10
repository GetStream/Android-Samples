package io.getstream.livestream.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import io.getstream.chat.android.compose.ui.theme.StreamColors

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
