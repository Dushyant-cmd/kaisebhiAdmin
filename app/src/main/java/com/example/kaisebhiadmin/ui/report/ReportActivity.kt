package com.example.kaisebhiadmin.ui.report

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.ReportAnsAdapter
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityReportBinding
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.example.kaisebhiadmin.utils.Utility
import kotlinx.coroutines.launch

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var viewModel: ReportViewModel
    private lateinit var reportAdapter: ReportAnsAdapter
    private val TAG: String = "ReportActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@ReportActivity, R.layout.activity_report)
        viewModel = ViewModelProvider(
            this@ReportActivity,
            ReportViewModelFactory(MainRepository((application as AppCustom).firebaseApiClass))
        )[ReportViewModel::class.java]
        binding.model = viewModel
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
//        throw RuntimeException("test")
        binding.reportAnsList.layoutManager = LinearLayoutManager(this@ReportActivity)
        reportAdapter = ReportAnsAdapter(this)
        binding.reportAnsList.adapter = reportAdapter
        getData()
        setListeners()
        setObservers()
    }

    private fun getData() {
        binding.shimmer.startShimmerAnimation()
        binding.shimmer.visibility = View.VISIBLE
        binding.reportAnsList.visibility = View.GONE
        lifecycleScope.launch {
            viewModel.getReportedAnswers().observe(this@ReportActivity) {
                it?.let {
                    reportAdapter.submitData(lifecycle, it)
                    binding.shimmer.stopShimmerAnimation()
                    binding.shimmer.visibility = View.GONE
                    binding.reportAnsList.visibility = View.VISIBLE
                }
            }
        }
        binding.swiperef.isRefreshing = false
    }

    private fun setListeners() {
        binding.swiperef.setOnRefreshListener {
            getData()
        }
    }

    private fun setObservers() {
//        viewModel.reportAnsLiveData.observe(this@ReportActivity) {
//            if(it is Success<*>) {
//                reportAdapter = ReportAnsAdapter(this, it.response as ArrayList<ReportedModel>, layoutInflater)
//                val layoutManager = LinearLayoutManager(this@ReportActivity)
//                binding.reportAnsList.layoutManager = layoutManager
//                binding.reportAnsList.adapter = reportAdapter
//                binding.shimmer.stopShimmerAnimation()
//                binding.shimmer.visibility = View.GONE
//                binding.reportAnsList.visibility = View.VISIBLE
//                Log.d(TAG, "setObservers: " + (it as Success<*>).response)
//            } else if(it is ResponseError){
//                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
//            }
//            binding.swiperef.isRefreshing = false
//        }

        viewModel.deleteAnsLiveData.observe(this@ReportActivity, Observer {
            if (it is Success<*>) {
                getData()
                binding.shimmer.stopShimmerAnimation()
                binding.shimmer.visibility = View.GONE
                binding.reportAnsList.visibility = View.VISIBLE
                Utility.showSnackBar(binding.root, it.response.toString())
                Log.d(TAG, "setObservers: ${it.response}")
            } else if (it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteAnswer(docId: String) {
        viewModel.deleteAnswer(docId)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}