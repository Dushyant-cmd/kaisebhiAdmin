package com.example.kaisebhiadmin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.launch

class SignInViewMode(private val mainRepository: MainRepository): ViewModel(){
    val adminLiveData: MutableLiveData<DocumentSnapshot> = MutableLiveData<DocumentSnapshot>()

    /**Below method will store adminCred in liveData*/
    fun getAdminCred() {
        viewModelScope.launch {
            adminLiveData.value = mainRepository.getAdminCred()
        }
    }
}