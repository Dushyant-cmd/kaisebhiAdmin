package com.example.kaisebhiadmin.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.utils.ResponseClass
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewMode(private val mainRepository: MainRepository): ViewModel(){
    val adminLiveData: MutableLiveData<ResponseClass> = MutableLiveData()

    /**Below method will store adminCred in liveData*/
    fun getAdminCred() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getAdminCred()
        }

        mainRepository.adminLiveData.observeForever {
            adminLiveData.value = it
        }
    }
}