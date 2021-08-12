package io.getstream.livestream.compose.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.livestream.compose.R
import io.getstream.livestream.compose.darkColorPalette
import io.getstream.livestream.compose.lightColorPalette

class LiveStreamHomeActivity : ComponentActivity() {
    private lateinit var sharedPref: SharedPreferences

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref =
            getSharedPreferences(getString(R.string.name_shared_pref), Context.MODE_PRIVATE)

        setContent {
            var expanded by remember { mutableStateOf(false) }
            var isDarkMode by remember {
                mutableStateOf(
                    sharedPref.getBoolean(
                        getString(R.string.key_theme),
                        false
                    )
                )
            }
            var isGrid by remember { mutableStateOf(true) }

            ChatTheme(colors = if (isDarkMode) darkColorPalette() else lightColorPalette()) {
                LiveStreamCustomChannelScreen(
                    title = resources.getString(R.string.app_name),
                    actions = {
                        // Adds a icon button for switching theme
                        IconButton(
                            onClick = {
                                expanded = !expanded
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_theme_switch),
                                contentDescription = stringResource(id = R.string.accessibilitySwitchTheme),
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        }
                        // Theme chooser drop down selector
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    expanded = !expanded
                                    isDarkMode = false
                                    updateTheme(isDarkMode)
                                }
                            ) {
                                Text(text = stringResource(R.string.light_theme_toggle_title))
                            }
                            DropdownMenuItem(
                                onClick = {
                                    expanded = !expanded
                                    isDarkMode = true
                                    updateTheme(isDarkMode)
                                }
                            ) {
                                Text(text = stringResource(R.string.dark_theme_toggle_title))
                            }
                        }

                        // Adds a icon button for switching grid toggle
                        IconButton(
                            onClick = {
                                isGrid = !isGrid
                            }
                        ) {
                            // We also update the icon when the state changes from a grid view to single list
                            val iconToShow =
                                if (!isGrid) {
                                    painterResource(id = R.drawable.ic_toggle_grid)
                                } else {
                                    painterResource(id = R.drawable.ic_toggle_list)
                                }
                            Icon(
                                painter = iconToShow,
                                contentDescription = stringResource(id = R.string.accessibilityGridToggle),
                                tint = ChatTheme.colors.textHighEmphasis
                            )
                        }
                    }
                ) {
                    LiveStreamChannels(
                        isDarkTheme = isDarkMode,
                        isGrid = isGrid,
                        modifier = Modifier.background(ChatTheme.colors.appBackground)
                    )
                }
            }
        }
    }

    /**
     * Simple function to store the last state of theme toggle into default preferences file.
     *
     * @param isDarkTheme - theme state after toggling from the dropdown
     */
    private fun updateTheme(isDarkTheme: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.key_theme), isDarkTheme)
            apply()
        }
    }
}
