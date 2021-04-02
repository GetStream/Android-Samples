package io.getstream.whatsappclone.ui.message_list

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
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
class MessageInputView : ConstraintLayout {
    private lateinit var binding: ViewMessageInputBinding

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        binding = ViewMessageInputBinding
            .inflate(LayoutInflater.from(context), this, true)
    }

    fun setViewModel(viewModel: MessageInputViewModel) {
        binding.voiceRecordingOrSend.setOnClickListener {

            viewModel.sendMessage(binding.messageInput.text.toString())
            binding.messageInput.setText("")
        }

        // listen to typing events and connect to the view model
        binding.messageInput.addTextChangedListener(
            object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    if (s.toString().isNotEmpty()) viewModel.keystroke()
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) = Unit
            }
        )
    }

}
