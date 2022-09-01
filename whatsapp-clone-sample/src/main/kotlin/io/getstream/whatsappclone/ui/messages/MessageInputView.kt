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

package io.getstream.whatsappclone.ui.messages

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
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
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ViewMessageInputBinding =
        ViewMessageInputBinding.inflate(LayoutInflater.from(context), this, true)

    fun initViews(sendMessage: (String) -> Unit, keystroke: () -> Unit) {
        binding.voiceRecordingOrSend.setOnClickListener {

            sendMessage.invoke(binding.messageInput.text.toString())
            binding.messageInput.setText("")
        }

        // listen to typing events and connect to the view model
        binding.messageInput.doAfterTextChanged {
            if (it.toString().isNotEmpty()) {
                keystroke.invoke()
                binding.takePicture.isVisible = false
                binding.voiceRecordingOrSend.setImageResource(R.drawable.ic_send_black_24dp)
            } else {
                binding.takePicture.isVisible = true
                binding.voiceRecordingOrSend.setImageResource(R.drawable.ic_mic_black_24dp)
            }
        }
    }
}
