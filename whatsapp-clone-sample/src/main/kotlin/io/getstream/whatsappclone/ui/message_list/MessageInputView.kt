package io.getstream.whatsappclone.ui.message_list

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import io.getstream.whatsappclone.R
import io.getstream.whatsappclone.databinding.ViewMessageInputBinding

/**
 * Basic message input view. Handles:
 * - Typing event
 * - Send message
 *
 * Doesn't handle more complex use cases like audio, video, file uploads, slash commands, editing text, threads or replies.
 * Stream's core API supports all of those though, so it's relatively easy to add
 *
 * When the user typed some text we change the microphone icon into a send button and hide the video button.
 */
class MessageInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewMessageInputBinding =
        ViewMessageInputBinding.inflate(LayoutInflater.from(context), this, true)
    private var micDrawable: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_mic_black_24dp)
    private var sendDrawable: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.ic_send_black_24dp)

    fun initViews(sendMessage: (String) -> Unit, keystroke: () -> Unit) {
        binding.voiceRecordingOrSend.setOnClickListener {

            sendMessage.invoke(binding.messageInput.text.toString())
//            viewModel.sendMessage(binding.messageInput.text.toString())
            binding.messageInput.setText("")
        }

        // listen to typing events and connect to the view model
        binding.messageInput.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                keystroke.invoke()
                binding.takePicture.isVisible = false
                binding.voiceRecordingOrSend.setImageDrawable(sendDrawable)
            } else {
                binding.takePicture.isVisible = true
                binding.voiceRecordingOrSend.setImageDrawable(micDrawable)
            }
        }
    }
}
