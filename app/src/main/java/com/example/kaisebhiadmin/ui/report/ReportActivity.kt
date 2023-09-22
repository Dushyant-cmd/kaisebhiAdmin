package com.example.kaisebhiadmin.ui.report

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityReportBinding
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.Success

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var viewModel: ReportViewModel
    private val TAG: String = "ReportActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ReportActivity, R.layout.activity_report)
        viewModel = ViewModelProvider(this@ReportActivity, ReportViewModelFactory(MainRepository((application as AppCustom).firebaseApiClass)))[ReportViewModel::class.java]
        binding.model = viewModel
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        viewModel.getReportedAnswers()
        setObservers()
    }

    private fun setObservers() {
        viewModel.reportAnsLiveData.observe(this@ReportActivity) {
            Log.d(TAG, "setObservers: " + (it as Success<*>).response)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}