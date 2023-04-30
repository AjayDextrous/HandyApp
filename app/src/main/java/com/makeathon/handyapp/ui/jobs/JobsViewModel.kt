package com.makeathon.handyapp.ui.jobs

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makeathon.handyapp.DataRepository
import com.makeathon.handyapp.MockDataRepositoryImpl
import com.makeathon.handyapp.R
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.ContactType
import com.makeathon.handyapp.models.Job
import com.makeathon.handyapp.models.ReportNote
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class JobsViewModel(app: Application) : AndroidViewModel(app) {
    fun checkInOrOut(currentAddress: String, isCheckIn: Boolean) {
        val check_inout_note = ReportNote(
            (currentJob?.reportNotes?.lastOrNull()?.noteId ?: 0) + 1, System.currentTimeMillis()).also {
            it.type = if (isCheckIn) {
                it.content = "I reached the location at ${it.creationTime.formatDateTime()}"
                ReportNote.NoteType.CHECKIN
            } else {
                it.content = "I left the location at ${it.creationTime.formatDateTime()}"
                ReportNote.NoteType.CHECKOUT
            }

        }
        currentJob?.reportNotes?.add(check_inout_note)
        reportNotesLiveData.value = currentJob?.reportNotes
    }

    fun sendTextMessage(message: String) {
        val timestamp = System.currentTimeMillis().formatDateTime()
        val reportNote = ReportNote(
            (currentJob?.reportNotes?.lastOrNull()?.noteId ?: 0) + 1, System.currentTimeMillis()
        ).also {
            it.sentBy = currentUser
            it.type = ReportNote.NoteType.TEXT
            it.content = message
        }
        currentJob?.reportNotes?.add(reportNote)
        reportNotesLiveData.value = currentJob?.reportNotes

    }

    fun sendReportNote(reportNote: ReportNote){
        currentJob?.reportNotes?.add(reportNote)
        reportNotesLiveData.value = currentJob?.reportNotes
    }

    fun sendImage(fileName: String, fileUri: Uri?, currentTime: Long) {
        val reportNote = ReportNote(reportNotesLiveData.value?.size?:0, currentTime).also {
            it.type = ReportNote.NoteType.PHOTO
            it.contentURI = fileUri
            it.fileName = fileName
            it.sentBy = currentUser
        }
        Log.d("sendImage()", reportNote.toString())
        currentJob?.reportNotes?.add(reportNote)
        reportNotesLiveData.value = currentJob?.reportNotes
    }

    fun sendAudio(audioUri: Uri?, currentTime: Long) {
        val reportNote = ReportNote(reportNotesLiveData.value?.size?:0, currentTime).also {
            it.type = ReportNote.NoteType.AUDIO
            it.contentURI = audioUri
            it.sentBy = currentUser
        }
        Log.d("sendAudio()", reportNote.toString())
        currentJob?.reportNotes?.add(reportNote)
        reportNotesLiveData.value = currentJob?.reportNotes
    }

    val dataRepository: DataRepository = MockDataRepositoryImpl()

    var currentJob: Job? = null

    var reportNotesLiveData = MutableLiveData<List<ReportNote>>(listOf())
    var currentUser: Contact = Contact(-1, "John Doe", ContactType.USER, R.drawable.user_1)

    private val _jobs = MutableLiveData<List<Job>>().apply {
        value = dataRepository.getJobs()
    }
    val jobsListLiveData: LiveData<List<Job>> = _jobs
}

fun Long.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a", Locale.getDefault())
    val instant = Instant.ofEpochMilli(this)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(dateTime)
}

fun Long.formatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
    val instant = Instant.ofEpochMilli(this)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return formatter.format(dateTime)
}