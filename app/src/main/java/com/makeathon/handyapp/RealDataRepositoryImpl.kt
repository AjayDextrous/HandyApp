package com.makeathon.handyapp

import android.util.Log
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.Job
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject

class RealDataRepositoryImpl : DataRepository {
    // Create an instance of OkHttpClient
    val client = OkHttpClient()
    val protocol = "http://"
    val baseURL = "jobbridgebackend.oa.r.appspot.com"



    override fun getJobs(): List<Job> {
        TODO("Not yet implemented")
    }



    override fun getContacts(): List<Contact> {
        TODO("Not yet implemented")
    }

    override fun getJob(jobId: Int): Job? {
        // Create a Request object
        val request = Request.Builder()
            .url("$protocol$baseURL/getJob")
            .build()

        var job: Job? = null
        val debugJson = "{\"jobId\": 1,\"jobTitle\": \"AC Repair\",\"jobDescription\": \"Checklist \\n- [] things to do no 1 \\n- [] things to do no. 2 \\n other stuff \",\"sumUp\": \"The service worker was called to check the AC compressor as the customer had reported knocking sounds. Upon inspection, the service worker realized that there wasn't enough oil sticking in the compressor. The issue was resolved by adding enough oil to the compressor, and the AC was functioning properly without any knocking sounds.\"}"
        try {
            val jobJson = JSONObject(debugJson)
            val jobId = jobJson.getInt("jobId")
            val jobTitle = jobJson.getString("jobTitle")
            val jobDescription = jobJson.getString("jobDescription")
            val addressString = jobJson.optString("addressString")?:"Beverly Hills 90210"
            val locationMapsLink = jobJson.optString("locationMapsLink")?:"https://goo.gl/maps/jwCdboZZcBGSbV4S7"
            val time = jobJson.optString("time")?:"4.04 PM"
            job = Job(jobId, jobTitle, jobDescription, addressString, locationMapsLink, time, isFinished = false)
        } catch (ex: JSONException) {
            Log.e("getJob()","Json Parse error: ${ex.message}")
        }
        return job
    }

}