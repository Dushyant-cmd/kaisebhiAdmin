package com.example.kaisebhiadmin.ui.withdraw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityWithdrawBinding
import com.example.kaisebhiadmin.utils.AppCustom

class WithdrawActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWithdrawBinding
    private val TAG = "WithdrawActivity.kt"
    private lateinit var viewModel: WithdrawViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdraw)
        val mainRepository = MainRepository((application as AppCustom).firebaseApiClass)
        viewModel = ViewModelProvider(this, WithdrawViewModelFactory(mainRepository))[WithdrawViewModel::class.java]

    }
}