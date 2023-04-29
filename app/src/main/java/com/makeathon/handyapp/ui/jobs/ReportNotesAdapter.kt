package com.makeathon.handyapp.ui.jobs

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
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
            binding.senderNameTextView.text = reportNote.sentBy?.contactName
            when (reportNote.type) {
                ReportNote.NoteType.CHECKIN, ReportNote.NoteType.CHECKOUT, ReportNote.NoteType.TEXT -> binding.chatMessageTextView.text = reportNote.content
                ReportNote.NoteType.AUDIO -> TODO()
                ReportNote.NoteType.PHOTO -> TODO()
                ReportNote.NoteType.VIDEO -> TODO()
            }
            when  {
                reportNote.type == ReportNote.NoteType.CHECKIN -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.blue_bubble))
                    binding.bubbleContainer.gravity = Gravity.CENTER
                }
                reportNote.type == ReportNote.NoteType.CHECKOUT -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.blue_bubble))
                    binding.bubbleContainer.gravity = Gravity.CENTER
                }
                reportNote.sentBy?.contactName == currentUser.contactName -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.yellow_bubble))
                    binding.bubbleContainer.gravity = Gravity.END
                }
                else -> {
                    binding.chatCard.setCardBackgroundColor(context.resources.getColor(R.color.green_bubble))
                    binding.bubbleContainer.gravity = Gravity.START
                }
            }
        }

    }

}

