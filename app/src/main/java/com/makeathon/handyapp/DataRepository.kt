package com.makeathon.handyapp

import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.Job

interface DataRepository {
    fun getJobs(): List<Job>
    fun getContacts(): List<Contact>
}