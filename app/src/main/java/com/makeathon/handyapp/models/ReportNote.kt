package com.makeathon.handyapp.models

class ReportNote (val noteId: Int, val creationTime: Long) {

    var sentBy: Contact? = null
    var type: NoteType = NoteType.TEXT
    var content: String? = null


    enum class NoteType {
        TEXT, AUDIO, PHOTO, VIDEO, CHECKIN, CHECKOUT
    }

}

