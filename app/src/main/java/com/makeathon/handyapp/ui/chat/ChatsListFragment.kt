package com.makeathon.handyapp.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makeathon.handyapp.R
import com.makeathon.handyapp.databinding.FragmentChatsBinding
import com.makeathon.handyapp.databinding.FragmentJobsBinding
import com.makeathon.handyapp.ui.jobs.JobAdapter

class ChatsListFragment: Fragment() {

    private val chatsAdapter: ContactAdapter? = null
    private var _binding: FragmentChatsBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chatsViewModel =
            ViewModelProvider(this).get(ChatsViewModel::class.java)

        _binding = FragmentChatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.chatsRecycler.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), R.drawable.divider_drawable)
        binding.chatsRecycler.addItemDecoration(dividerItemDecoration)

        chatsViewModel.chatsListLiveData.observe(viewLifecycleOwner) { chats ->
            var chatsAdapter = this.chatsAdapter
            if(chatsAdapter == null){
                chatsAdapter = ContactAdapter(chats)
                binding.chatsRecycler.adapter = chatsAdapter
            } else {
                chatsAdapter.updateChats(chats)
                chatsAdapter.notifyDataSetChanged()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}