package com.makeathon.handyapp.ui.jobs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makeathon.handyapp.databinding.ListitemJobBinding
import com.makeathon.handyapp.models.Job

class JobAdapter(private var jobList: List<Job>, private val listener: OnItemClickListener?) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val itemBinding = ListitemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val currentJob = jobList[position]
        holder.bind(currentJob)

        holder.itemView.setOnClickListener {
            listener?.onClick(currentJob)
        }
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    fun updateJobs(jobs: List<Job>) {
        jobList = jobs
    }

    inner class JobViewHolder(private val itemBinding: ListitemJobBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(job: Job){
            itemBinding.title.text = job.jobTitle
            itemBinding.description.text = job.jobDescription
            itemBinding.address.text = job.addressString
            itemBinding.time.text = job.time
        }
    }

    interface OnItemClickListener{
        fun onClick(job: Job)
    }
}
