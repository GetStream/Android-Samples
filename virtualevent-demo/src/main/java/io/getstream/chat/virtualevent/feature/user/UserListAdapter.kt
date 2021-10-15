package io.getstream.chat.virtualevent.feature.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.User
import io.getstream.chat.virtualevent.databinding.ItemUserBinding

class UserListAdapter(
    private val userClickListener: (user: User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    private val users: MutableList<User> = mutableListOf()

    fun setUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .let { UserViewHolder(it, userClickListener) }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class UserViewHolder(
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
            }
        }
    }
}
