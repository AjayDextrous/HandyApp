package com.makeathon.handyapp

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.Job
import com.makeathon.handyapp.models.ReportNote
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

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
        val debugJson =
            "{\"jobId\": 1,\"jobTitle\": \"AC Repair\",\"jobDescription\": \"Checklist \\n- [] things to do no 1 \\n- [] things to do no. 2 \\n other stuff \",\"sumUp\": \"The service worker was called to check the AC compressor as the customer had reported knocking sounds. Upon inspection, the service worker realized that there wasn't enough oil sticking in the compressor. The issue was resolved by adding enough oil to the compressor, and the AC was functioning properly without any knocking sounds.\"}"
        try {
            val jobJson = JSONObject(debugJson)
            val jobId = jobJson.getInt("jobId")
            val jobTitle = jobJson.getString("jobTitle")
            val jobDescription = jobJson.getString("jobDescription")
            val addressString = jobJson.optString("addressString") ?: "Beverly Hills 90210"
            val locationMapsLink =
                jobJson.optString("locationMapsLink") ?: "https://goo.gl/maps/jwCdboZZcBGSbV4S7"
            val time = jobJson.optString("time") ?: "4.04 PM"
            job = Job(
                jobId,
                jobTitle,
                jobDescription,
                addressString,
                locationMapsLink,
                time,
                isFinished = false
            )
        } catch (ex: JSONException) {
            Log.e("getJob()", "Json Parse error: ${ex.message}")
        }
        return job
    }

    override fun postBulk(job: Job, reportNotes: List<ReportNote>) {

        val jsonObject = JSONObject()
        jsonObject.put("jobId", job.jobId)
        jsonObject.put("jobTitle", job.jobTitle)
        jsonObject.put("jobDescription", job.jobDescription)
        val jsonArray = JSONArray()
        reportNotes.forEach {

        }

        jsonObject.put("notes", jsonArray)
    }

    override fun postPhoto(reportNote: ReportNote, context: Context) {
        val json =
            "{\"photoId\":\"${reportNote.noteId}\", \"image\":\"data:image/jpg;base64,encoded_image_data\", \"${reportNote.fileName}\"}"

        val inputStream = context.contentResolver.openInputStream(reportNote.contentURI!!)
        val imageBytes = inputStream?.readBytes()
        val base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)

//        val requestBody = MultipartBody.Builder()
//            .setType(MultipartBody.FORM)
//            .addFormDataPart("image", reportNote.fileName, imageFile.asRequestBody("image/*".toMediaTypeOrNull()))
//            .build()

        val requestBody = FormBody.Builder()
            .add("photoId", reportNote.noteId.toString())
            .add("image", base64Image)
            .add("filename", reportNote.fileName ?: "photo_${reportNote.noteId}")
            .build()

        val request = Request.Builder()
            .url("$protocol$baseURL/postPhoto")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("postPhoto()", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("postPhoto()", "response = $response")
            }

        })
        inputStream?.close()
    }


}