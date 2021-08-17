package io.getstream.slackclonesample.feature.chat

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleOwner
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import io.getstream.chat.android.client.models.Member
import io.getstream.chat.android.ui.message.input.MessageInputView
import io.getstream.slackclonesample.databinding.CustomMessageInputBinding

@JvmName("bind")
public fun MessageInputViewModel.bindView(
    bindingView: CustomMessageInputBinding,
    lifecycleOwner: LifecycleOwner
) {
    val handler = MessageInputView.DefaultUserLookupHandler(emptyList())
    members.observe(lifecycleOwner) { members ->
        handler.users = members.map(Member::user)
    }

    bindingView.sendMessage.setOnClickListener {
        this.sendMessage(bindingView.messageInput.text.toString())
        bindingView.messageInput.setText("")
    }

    // listen to typing events and connect to the view model
    bindingView.messageInput.addTextChangedListener(TypingTextWatcher(this))
}

class TypingTextWatcher(private val viewModel: MessageInputViewModel) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun afterTextChanged(s: Editable?) {
        if (s.toString().isNotEmpty()) {
            viewModel.keystroke()
        }
    }

}