package io.getstream.slack.compose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import io.getstream.chat.android.compose.ui.theme.StreamColors
import io.getstream.slack.compose.R

@Composable
fun slackLightColors(): StreamColors = StreamColors(
    textHighEmphasis = colorResource(R.color.text_high_emphasis),
    textLowEmphasis = colorResource(R.color.text_low_emphasis),
    disabled = colorResource(R.color.disabled),
    borders = colorResource(R.color.borders),
    inputBackground = colorResource(R.color.input_background),
    appBackground = colorResource(R.color.app_background),
    barsBackground = colorResource(R.color.bars_background),
    linkBackground = colorResource(R.color.link_background),
    overlay = colorResource(R.color.overlay_regular),
    overlayDark = colorResource(R.color.overlay_dark),
    primaryAccent = colorResource(R.color.primary_accent),
    errorAccent = colorResource(R.color.error_accent),
    infoAccent = colorResource(R.color.info_accent),
    highlight = colorResource(R.color.highlight),
)

@Composable
fun slackDarkColors(): StreamColors = StreamColors(
    textHighEmphasis = colorResource(R.color.text_high_emphasis_dark),
    textLowEmphasis = colorResource(R.color.text_low_emphasis_dark),
    disabled = colorResource(R.color.disabled_dark),
    borders = colorResource(R.color.borders_dark),
    inputBackground = colorResource(R.color.input_background_dark),
    appBackground = colorResource(R.color.app_background_dark),
    barsBackground = colorResource(R.color.bars_background_dark),
    linkBackground = colorResource(R.color.link_background_dark),
    overlay = colorResource(R.color.overlay_regular_dark),
    overlayDark = colorResource(R.color.overlay_dark_dark),
    primaryAccent = colorResource(R.color.primary_accent_dark),
    errorAccent = colorResource(R.color.error_accent_dark),
    infoAccent = colorResource(R.color.info_accent_dark),
    highlight = colorResource(R.color.highlight_dark),
)
