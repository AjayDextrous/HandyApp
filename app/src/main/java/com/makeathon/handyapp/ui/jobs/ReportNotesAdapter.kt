package com.makeathon.handyapp.ui.jobs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.recyclerview.widget.RecyclerView
import com.makeathon.handyapp.R
import com.makeathon.handyapp.databinding.ListitemReportNoteBinding
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.ReportNote

class ReportNotesAdapter(var reportNotes: List<ReportNote>, val currentUser: Contact) : RecyclerView.Adapter<ReportNotesAdapter.ReportNotesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportNotesViewHolder {
        val itemBinding = ListitemReportNoteBinding.inflate(LayoutInflater.from(parent.context), parent , false)
        return ReportNotesViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ReportNotesViewHolder, position: Int) {
        val currentNote = reportNotes[position]
        holder.bind(currentNote, currentUser)
    }

    override fun getItemCount(): Int {
        return reportNotes.size
    }

    class ReportNotesViewHolder(private val binding: ListitemReportNoteBinding, val context: Context): RecyclerView.ViewHolder(binding.root) {

        fun bind(reportNote: ReportNote, currentUser: Contact){
            // resetting values
            binding.chatMessageTextView.visibility = View.VISIBLE


            binding.senderNameTextView.text = reportNote.sentBy?.contactName
            binding.timeTextView.text = reportNote.creationTime.formatTime()
            when (reportNote.type) {
                ReportNote.NoteType.CHECKIN, ReportNote.NoteType.CHECKOUT, ReportNote.NoteType.TEXT -> {
                    binding.chatMessageTextView.text = reportNote.content
                }
                ReportNote.NoteType.AUDIO -> {
                    binding.audioFileName.text = context.getString(R.string.voice_recording, reportNote.creationTime.formatDateTime())
                    binding.playerLayout.visibility = View.VISIBLE
                    binding.chatMessageTextView.visibility = View.GONE
                }
                ReportNote.NoteType.PHOTO -> {
                    binding.chatMessageTextView.visibility = View.GONE
                    binding.chatMessageImageView.visibility = View.VISIBLE
                    binding.chatMessageImageView.setImageURI(reportNote.contentURI)
                }
                ReportNote.NoteType.VIDEO -> TODO()
            }
            when  {
                reportNote.type == ReportNote.NoteType.CHECKIN -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.blue_bubble))
                    binding.bubbleContainer.gravity = Gravity.CENTER
                    binding.senderNameTextView.visibility = View.GONE
                    binding.timeTextView.visibility = View.GONE
                    binding.chatMessageTextView.setTextColor(context.getColor(R.color.textcolor_light))
                }
                reportNote.type == ReportNote.NoteType.CHECKOUT -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.blue_bubble))
                    binding.bubbleContainer.gravity = Gravity.CENTER
                    binding.senderNameTextView.visibility = View.GONE
                    binding.timeTextView.visibility = View.GONE
                    binding.chatMessageTextView.setTextColor(context.getColor(R.color.textcolor_light))
                }
                reportNote.type == ReportNote.NoteType.AUDIO -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.white_bubble))
                    binding.bubbleContainer.gravity = Gravity.END
                }
                reportNote.sentBy?.contactName == currentUser.contactName -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.yellow_bubble))
                    binding.bubbleContainer.gravity = Gravity.END
                    binding.timeTextView.visibility = View.VISIBLE
                    binding.senderNameTextView.visibility = View.VISIBLE
                    binding.senderNameTextView.setTextColor(context.getColor(R.color.textcolor_dark))
                    binding.chatMessageTextView.setTextColor(context.getColor(R.color.textcolor_dark))
                }
                else -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.green_bubble))
                    binding.bubbleContainer.gravity = Gravity.START
                    binding.timeTextView.visibility = View.VISIBLE
                    binding.senderNameTextView.visibility = View.VISIBLE
                    binding.senderNameTextView.setTextColor(context.getColor(R.color.textcolor_dark))
                    binding.chatMessageTextView.setTextColor(context.getColor(R.color.textcolor_dark))
                }
            }
        }

    }

}

