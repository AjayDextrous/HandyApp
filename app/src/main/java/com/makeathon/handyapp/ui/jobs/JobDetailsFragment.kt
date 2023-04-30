package com.makeathon.handyapp.ui.jobs

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeathon.handyapp.R
import com.makeathon.handyapp.databinding.FragmentJobDetailsBinding
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.ContactType
import com.makeathon.handyapp.models.ReportNote
import java.io.File
import java.io.FileOutputStream


class JobDetailsFragment : Fragment() {
    private var photoFile: File? = null
    private lateinit var jobsViewModel: JobsViewModel
    private var _binding: FragmentJobDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var recordAudioLauncher: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jobsViewModel =
            ViewModelProvider(requireActivity())[JobsViewModel::class.java]

        _binding = FragmentJobDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val currentJob = jobsViewModel.currentJob!!
        binding.titleTextView.text = currentJob.jobTitle
        currentJob.thumbnail?.let { binding.titleImage.setImageResource(it) }
        binding.timeTextView.text = currentJob.time
        binding.navigateCard.setOnClickListener {
            val uri = Uri.parse(currentJob.locationMapsLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "No app found to handle \"${currentJob.locationMapsLink}\"", Toast.LENGTH_SHORT).show()
            }
        }

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
        binding.chatRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.chatRecycler.adapter = reportNotesAdapter

        jobsViewModel.reportNotesLiveData.observe(viewLifecycleOwner){
            reportNotesAdapter.reportNotes = it
            reportNotesAdapter.notifyItemInserted(it.size-1)
        }

        binding.sendButton.setOnClickListener {
            val message = binding.chatTextInput.text.toString()
            jobsViewModel.sendTextMessage(message)
            binding.chatTextInput.setText("")
            binding.chatTextInput.clearFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.chatTextInput.windowToken, 0)
        }

        binding.imageButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photoFile = null
            photoFile = createImageFile()
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(requireContext(), "com.makeathon.fileprovider", photoFile!!))
                takePictureLauncher.launch(takePictureIntent)
            }
        }
        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val currentTime = System.currentTimeMillis()
                val fileUri = FileProvider.getUriForFile(requireContext(), "com.makeathon.fileprovider", photoFile!!)
                jobsViewModel.sendImage(photoFile!!.name, fileUri, currentTime)
            } else {
                Toast.makeText(requireContext(), "Could Not Capture File!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.audioButton.setOnClickListener {
            val recordAudioIntent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
            recordAudioIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            recordAudioLauncher.launch(recordAudioIntent)
        }
        recordAudioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val audioUri = result.data?.data
            val currentTime = System.currentTimeMillis()
            jobsViewModel.sendAudio(audioUri, currentTime)

        }

        val boss = Contact(2,"Martha K.", ContactType.BOSS, R.drawable.boss_1,null, "")
        val reportNote = ReportNote(0, System.currentTimeMillis()).also {
            it.type = ReportNote.NoteType.TEXT
            it.content = currentJob.jobDescription
            it.sentBy = boss
        }

        jobsViewModel.sendReportNote(reportNote)

        return root
    }

    private fun createImageFile(): File? {
        val timeStamp = System.currentTimeMillis()
        val imageFileName = "handy_img_$timeStamp"
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
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
            if(binding.checkinCheckoutSwitch.isChecked){
                binding.startJobTextview.text = getString(R.string.end_job)
            } else {
                binding.startJobTextview.text = getString(R.string.start_job)
            }
            jobsViewModel.currentJob?.reportNotes?.size?.let {
                binding.chatRecycler.scrollToPosition(it - 1)
            }

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
        private const val REQUEST_IMAGE_CAPTURE = 101
    }
}