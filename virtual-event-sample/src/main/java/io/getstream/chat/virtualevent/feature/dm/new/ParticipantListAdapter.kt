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

package io.getstream.chat.virtualevent.feature.dm.new

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.getstream.chat.android.client.models.User
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
