package io.getstream.slack.compose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.getstream.chat.android.compose.ui.theme.StreamTypography
import io.getstream.slack.compose.R

private val light = Font(R.font.lato_light, FontWeight.W300)
private val regular = Font(R.font.lato_regular, FontWeight.W400)
private val medium = Font(R.font.lato_regular, FontWeight.W500)
private val bold = Font(R.font.lato_bold, FontWeight.W700)

private val slackFontFamily = FontFamily(fonts = listOf(light, regular, medium, bold))

/**
 * The default [StreamTypography] from the SDK with a custom font family
 * used by Slack ([Lato](https://fonts.google.com/specimen/Lato)).
 */
val slackTypography: StreamTypography = StreamTypography
    .defaultTypography()
    .withFontFamily(slackFontFamily)

/**
 * Returns a copy of [StreamTypography] with a custom font family applied to
 * every text style..
 *
 * @param fontFamily The font family to be used with every text style.
 */
private fun StreamTypography.withFontFamily(fontFamily: FontFamily): StreamTypography {
    return copy(
        title1 = title1.withFontFamily(fontFamily),
        title3 = title3.withFontFamily(fontFamily),
        title3Bold = title3Bold.withFontFamily(fontFamily),
        body = body.withFontFamily(fontFamily).copy(fontSize = 15.sp),
        bodyItalic = bodyItalic.withFontFamily(fontFamily),
        bodyBold = bodyBold.withFontFamily(fontFamily),
        footnote = footnote.withFontFamily(fontFamily),
        footnoteItalic = footnoteItalic.withFontFamily(fontFamily),
        footnoteBold = footnoteBold.withFontFamily(fontFamily),
        captionBold = captionBold.withFontFamily(fontFamily),
        tabBar = tabBar.withFontFamily(fontFamily)
    )
}

/**
 * Returns a copy of [TextStyle] with a custom font family.
 *
 * @param fontFamily The font family to be used with this text style.
 */
private fun TextStyle.withFontFamily(fontFamily: FontFamily): TextStyle {
    return copy(fontFamily = fontFamily)
}