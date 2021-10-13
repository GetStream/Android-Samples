package io.getstream.slack.compose.ui.messages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

class MessagesActivity : AppCompatActivity() {

    private val factory: MessagesViewModelFactory by lazy {
        return@lazy MessagesViewModelFactory(
            context = this,
            channelId = intent.getStringExtra(KEY_CHANNEL_ID) ?: "",
        )
    }

    private val listViewModel by viewModels<MessageListViewModel>(::factory)
    private val composerViewModel by viewModels<MessageComposerViewModel>(::factory)
    private val attachmentsPickerViewModel by viewModels<AttachmentsPickerViewModel>(::factory)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlackMessagesTheme {
                SetupSystemUI()
                MessagesScreen(
                    listViewModel = listViewModel,
                    composerViewModel = composerViewModel,
                    attachmentsPickerViewModel = attachmentsPickerViewModel,
                    onBackPressed = { finish() },
                )
            }
        }
    }

    /**
     * Responsible for updating the system UI.
     */
    @Composable
    private fun SetupSystemUI() {
        val systemUiController = rememberSystemUiController()

        val statusBarColor = ChatTheme.colors.barsBackground
        val navigationBarColor = ChatTheme.colors.barsBackground

        val isDarkTheme = isSystemInDarkTheme()
        SideEffect {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = !isDarkTheme
            )
            systemUiController.setNavigationBarColor(
                color = navigationBarColor,
                darkIcons = !isDarkTheme
            )
        }
    }

    companion object {
        private const val KEY_CHANNEL_ID = "channelId"

        fun getIntent(context: Context, channelId: String): Intent {
            return Intent(context, MessagesActivity::class.java).apply {
                putExtra(KEY_CHANNEL_ID, channelId)
            }
        }
    }
}
