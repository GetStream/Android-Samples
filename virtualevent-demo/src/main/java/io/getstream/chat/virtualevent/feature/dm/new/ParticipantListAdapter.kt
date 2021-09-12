package io.getstream.chat.virtualevent.feature.dm.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.name
import io.getstream.chat.virtualevent.databinding.ItemUserBinding

class ParticipantListAdapter(
    private val userClickListener: (user: User) -> Unit
) : RecyclerView.Adapter<ParticipantListAdapter.ParticipantViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    fun setUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        return ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { ParticipantViewHolder(it, userClickListener) }
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class ParticipantViewHolder(
        private val binding: ItemUserBinding,
        private val userClickListener: (user: User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var user: User

        init {
            binding.root.setOnClickListener {
                userClickListener(user)
            }
        }

        fun bind(user: User) {
            this.user = user
            with(binding) {
                userAvatarView.setUserData(user)
                nameTextView.text = user.name
                companyTextView.text = randomCompany()
            }
        }
    }

    companion object {
        /**
         * Mock user companies according to the design.
         */
        private val COMPANY_LIST: Set<String> = setOf(
            "Reilly LLC",
            "Miller, O'Conner and Nicolas",
            "Grimes, Bashirian and Nolan",
            "Borer, Hamill and Mante",
            "Steuber Inc",
            "Baumbach, Weissnat and Herzog",
            "Upton, Pfeffer and Hodkiewicz",
            "Mraz Group",
            "Harvey Inc",
            "Kihn LLC",
            "Designer. Speaker, Lifelong Learner.",
            "Feest - Lakin",
            "Kihn - Parisian",
            "Nolan Inc",
            "Keebler LLC",
            "Huels - Daniel"
        )

        /**
         * Returns a random company name.
         */
        fun randomCompany(): String = COMPANY_LIST.random()
    }
}
