package com.makeathon.handyapp.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makeathon.handyapp.DataRepository
import com.makeathon.handyapp.MockDataRepositoryImpl
import com.makeathon.handyapp.models.Contact
import com.makeathon.handyapp.models.Job

class ChatsViewModel : ViewModel() {

    val dataRepository: DataRepository = MockDataRepositoryImpl()

    private val _chats = MutableLiveData<List<Contact>>().apply {
        value = dataRepository.getContacts()
    }
    val chatsListLiveData: LiveData<List<Contact>> = _chats
}