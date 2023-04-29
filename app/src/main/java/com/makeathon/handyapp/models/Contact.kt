package com.makeathon.handyapp.models

class Contact(contactId: Int, var contactName: String, var contactType: ContactType, var profilePic: Int, var lastMessage: String? = null, var lastMessageTime: String = "Just now") {

}

enum class ContactType {
    BOSS, CUSTOMER, COWORKER, USER
}