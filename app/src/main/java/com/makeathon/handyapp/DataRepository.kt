package com.makeathon.handyapp

import android.content.Context
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.Job
import com.makeathon.handyapp.models.ReportNote

interface DataRepository {
    fun getJobs(): List<Job>
    fun getContacts(): List<Contact>
    fun getJob(jobId: Int): Job?
    fun postBulk(job: Job, reportNotes: List<ReportNote>)
    fun postPhoto(reportNote: ReportNote, context: Context)
}