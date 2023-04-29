package com.makeathon.handyapp.models

class Job(jobId: Int, var jobTitle: String, var jobDescription: String, var addressString: String, var locationMapsLink: String, var time: String, var isFinished: Boolean = false) {

 val reportNotes: MutableList<ReportNote> = mutableListOf()
}