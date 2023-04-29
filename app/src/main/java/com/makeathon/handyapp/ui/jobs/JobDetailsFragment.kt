package com.makeathon.handyapp.ui.jobs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeathon.handyapp.R
import com.makeathon.handyapp.databinding.FragmentJobDetailsBinding
import com.makeathon.handyapp.models.ReportNote
import java.io.IOException
import java.util.*


class JobDetailsFragment : Fragment() {
    private lateinit var jobsViewModel: JobsViewModel
    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jobsViewModel =
            ViewModelProvider(requireActivity())[JobsViewModel::class.java]

        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.checkinCheckoutButton.setOnClickListener {
            binding.checkinCheckoutSwitch.toggle()
            binding.startJobTextview.visibility = View.GONE
            binding.checkinProgressbar.visibility = View.VISIBLE
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    Companion.LOCATION_PERMISSION_REQUEST_CODE
                );
            } else {
                getCurrentLocation();
            }
        }

        val reportNotesAdapter = ReportNotesAdapter(jobsViewModel.currentJob?.reportNotes!!, jobsViewModel.currentUser)
        binding.chatRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        binding.chatRecycler.adapter = reportNotesAdapter

        jobsViewModel.reportNotesLiveData.observe(viewLifecycleOwner){
            reportNotesAdapter.reportNotes = it
            reportNotesAdapter.notifyItemInserted(it.size-1)
        }

        return root
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        // Create a location manager instance
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        // Request the last known location from the location manager
        val location: Location? =
            locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        // Check if the location is available
        if (location != null) {
            val currentLocation = location.toString()
            jobsViewModel.checkInOrOut(
                currentLocation,
                binding.checkinCheckoutSwitch.isChecked
            )
            // Create a geocoder instance
//            val geocoder = Geocoder(requireContext(), Locale.getDefault())
//            try {
//                // Get the address list for the current location
////                val addresses: List<Address>? =
////                    geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1)
//                if (!addresses!!.isEmpty()) {
//                    val address: Address = addresses[0]
//                    // Use the address object to access the location details
//                    val currentAddress: String = address.getAddressLine(0)
//
//
//                }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
            binding.startJobTextview.text = getString(R.string.end_job)

        } else {
            binding.startJobTextview.text = getString(R.string.error_checkin)
            binding.checkinCheckoutSwitch.toggle()
        }
        binding.startJobTextview.visibility = View.VISIBLE
        binding.checkinProgressbar.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
                binding.checkinCheckoutSwitch.toggle()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                binding.checkinCheckoutSwitch.toggle()
                // Location permission denied
                // Handle the case when the user denies the permission request
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1;
    }
}