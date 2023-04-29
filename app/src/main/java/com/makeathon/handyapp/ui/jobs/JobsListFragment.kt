package com.makeathon.handyapp.ui.jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeathon.handyapp.databinding.FragmentJobsBinding
import com.makeathon.handyapp.models.Job

class JobsListFragment : Fragment() {

    private var jobsAdapter: JobAdapter? = null

    private var _binding: FragmentJobsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val jobsViewModel =
            ViewModelProvider(requireActivity())[JobsViewModel::class.java]

        _binding = FragmentJobsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.jobRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        jobsViewModel.jobsListLiveData.observe(viewLifecycleOwner) { jobs ->
            var jobsAdapter = this.jobsAdapter
            if (jobsAdapter == null) {
                jobsAdapter = JobAdapter(jobs, object : JobAdapter.OnItemClickListener {
                    override fun onClick(job: Job) {
                        jobsViewModel.currentJob = job
                        val action = JobsListFragmentDirections.actionJobsToJobDetails()
                        findNavController().navigate(action)
                    }

                })
                binding.jobRecycler.adapter = jobsAdapter
            } else {
                jobsAdapter.updateJobs(jobs)
                jobsAdapter.notifyDataSetChanged()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}