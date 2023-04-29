package com.makeathon.handyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.makeathon.handyapp.ui.jobs.JobDetailsFragment

class JobDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportFragmentManager.beginTransaction()
            .replace(R.id.details_fragment, JobDetailsFragment()).commit()
    }
}