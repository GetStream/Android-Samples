package io.getstream.chat.virtualevent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.virtualevent.databinding.ActivityMainBinding
import io.getstream.chat.virtualevent.feature.user.SwitchUserActivity
import io.getstream.chat.virtualevent.util.ThemeHelper
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeHelper: ThemeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        ChatClient.instance().getCurrentUser()?.let {
            binding.userAvatarView.setUserData(it)
        }
        binding.userAvatarView.setOnClickListener {
            startActivity(SwitchUserActivity.createIntent(this))
        }

        themeHelper = ThemeHelper(applicationContext)
        binding.themeSwitchImageView.setOnClickListener {
            themeHelper.toggleDarkTheme()
        }
    }

    private fun setupBottomNavigation() {
        with(binding.bottomNavigationView) {
            // disable reloading fragment when clicking again on the same tab
            setOnNavigationItemReselectedListener {}

            val navController = findNavController(R.id.navHostFragment)
            setupWithNavController(navController)
            navController.addOnDestinationChangedListener { _, destination, _ ->
                val titleRes = when (destination.id) {
                    R.id.navigation_overview -> R.string.title_overview
                    R.id.navigation_events -> R.string.title_events
                    R.id.navigation_dm -> R.string.title_dm
                    else -> throw IllegalArgumentException("Unsupported navigation item")
                }

                binding.toolbarTitleTextView.text = getString(titleRes)
            }
        }
    }

    companion object {
        fun createIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }
}
