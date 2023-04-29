package com.makeathon.handyapp.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makeathon.handyapp.R
import com.makeathon.handyapp.databinding.FragmentSettingsBinding
import com.makeathon.handyapp.models.Setting
import com.makeathon.handyapp.models.SettingsItem
import com.makeathon.handyapp.ui.chat.DividerItemDecoration

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.settingsRecycler

        val itemList = listOf(
            SettingsItem(R.drawable.ic_baseline_perm_identity_24, "Profile Details", Setting.PROFILE),
            SettingsItem(R.drawable.ic_baseline_live_help_24, "Help", Setting.HELP),
            SettingsItem(R.drawable.ic_baseline_info_24, "About", Setting.ABOUT),
        )

        val adapter = SettingsAdapter(requireContext(), itemList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(requireContext(), R.drawable.divider_drawable)
        binding.settingsRecycler.addItemDecoration(dividerItemDecoration)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}