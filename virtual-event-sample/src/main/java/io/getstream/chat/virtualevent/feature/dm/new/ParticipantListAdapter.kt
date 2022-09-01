/*
 *  The MIT License (MIT)
 *
 *  Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
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
