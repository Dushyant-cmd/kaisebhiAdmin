package com.example.kaisebhiadmin.ui.withdraw

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.utils.ResponseClass

/**Below is ViewModel class which will handle all the business logic work. and cleared
 * only if lifecycle owner is finished like view(activity/fragment) */
class WithdrawViewModel(val mainRepository: MainRepository): ViewModel() {
    val withdrawLivedata: LiveData<ResponseClass>
        get() = mainRepository.withdrawLiveData
    fun getWithdrawals() {

    }
}