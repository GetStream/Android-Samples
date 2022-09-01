/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.chat.virtualevent

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import io.getstream.chat.virtualevent.databinding.ActivityMainBinding
import io.getstream.chat.virtualevent.feature.user.SwitchUserActivity
import io.getstream.chat.virtualevent.util.ThemeHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var themeHelper: ThemeHelper

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupToolbar() {
        viewModel.currentUser.observe(this) { user ->
            if (user != null) {
                binding.userAvatarView.setUserData(user)
            }
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
