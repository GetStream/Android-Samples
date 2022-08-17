package io.getstream.chat.virtualevent.util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class ThemeHelper(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    init {
        val isDark = isDarkTheme()
        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun toggleDarkTheme() {
        val darkTheme = isDarkTheme()
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        prefs.edit { putBoolean(KEY_DARK, !darkTheme) }
    }

    private fun isDarkTheme(): Boolean = prefs.getBoolean(KEY_DARK, false)

    companion object {
        private const val PREFS_NAME = "prefs_dark"
        private const val KEY_DARK = "key_dark"
    }
}
