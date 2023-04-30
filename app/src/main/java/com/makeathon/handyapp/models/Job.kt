package com.makeathon.handyapp.models

class Job(val jobId: Int, var jobTitle: String, var jobDescription: String, var addressString: String, var locationMapsLink: String, var time: String, var isFinished: Boolean = false, var thumbnail: Int? = null) {

 val reportNotes: MutableList<ReportNote> = mutableListOf()
}