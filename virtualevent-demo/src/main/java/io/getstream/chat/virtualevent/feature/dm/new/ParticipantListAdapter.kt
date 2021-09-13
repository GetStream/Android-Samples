package io.getstream.chat.virtualevent.feature.dm.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.virtualevent.databinding.ItemParticipantBinding

class ParticipantListAdapter(
    private val participantClickListener: (user: User) -> Unit
) : RecyclerView.Adapter<ParticipantListAdapter.ParticipantViewHolder>() {

    private val participants: MutableList<User> = mutableListOf()

    fun setParticipants(participants: List<User>) {
        this.participants.clear()
        this.participants.addAll(participants)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = participants.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        return ItemParticipantBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { ParticipantViewHolder(it, participantClickListener) }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(participants[position])
    }

    class ParticipantViewHolder(
        private val binding: ItemParticipantBinding,
        private val participantClickListener: (user: User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var participants: User

        init {
            binding.root.setOnClickListener {
                participantClickListener(participants)
            }
        }

        fun bind(participants: User) {
            this.participants = participants
            with(binding) {
                userAvatarView.setUserData(participants)
                nameTextView.text = participants.name
                companyTextView.text = participants.extraData[EXTRA_COMPANY] as? String
            }
        }
    }

    companion object {
        private const val EXTRA_COMPANY = "company"
    }
}
