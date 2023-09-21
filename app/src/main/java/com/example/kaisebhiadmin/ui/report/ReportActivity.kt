package com.example.kaisebhiadmin.ui.report

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private val TAG: String = "ReportActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ReportActivity, R.layout.activity_report)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}