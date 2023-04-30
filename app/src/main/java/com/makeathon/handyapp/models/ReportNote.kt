package com.makeathon.handyapp.models

import android.net.Uri

class ReportNote (val noteId: Int, val creationTime: Long) {

    var sentBy: Contact? = null
    var type: NoteType = NoteType.TEXT
    var content: String? = null
    var contentURI: Uri? = null


    enum class NoteType {
        TEXT, AUDIO, PHOTO, VIDEO, CHECKIN, CHECKOUT
    }

    override fun toString(): String {
        return "ReportNote(noteId=$noteId, creationTime=$creationTime, " +
                "sentBy=$sentBy, type=$type, content=$content, contentURI=$contentURI)"
    }

}

