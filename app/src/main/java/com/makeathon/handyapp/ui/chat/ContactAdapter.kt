package com.makeathon.handyapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeathon.handyapp.databinding.ListitemChatBinding
import com.makeathon.handyapp.models.Contact

class ContactAdapter(private var contactList: List<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListitemChatBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.bind(currentContact)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun updateChats(chats: List<Contact>) {
        this.contactList = chats
    }

    inner class ContactViewHolder(private val binding: ListitemChatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.apply {
                profileImageView.setImageResource(contact.profilePic)
                nameTextView.text = contact.contactName
                lastMessageTextView.text = contact.lastMessage?:"No Chats Available"
                timeTextView.text = contact.lastMessageTime

                root.setOnClickListener {
                    // Handle item click event
                }
            }
        }
    }
}
