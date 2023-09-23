package com.example.kaisebhiadmin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.databinding.ReportListItemBinding
import com.example.kaisebhiadmin.models.ReportedModel
import com.example.kaisebhiadmin.ui.report.ReportActivity

class ReportAnsAdapter(val ctx: Context,
    val list: ArrayList<ReportedModel>,
    val layoutInflater: LayoutInflater): RecyclerView.Adapter<ReportAnsAdapter.ViewHolder>() {
    private val TAG: String = "ReportAnsAdapter.kt"
    lateinit var binding: ReportListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAnsAdapter.ViewHolder {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.report_list_item, parent,
            false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ReportAnsAdapter.ViewHolder, position: Int) {
        val model = list[position]
        val activity = ctx as ReportActivity
        binding.titleTv.text = model.ans
        binding.reportByTv.text = "Reported by ${model.reportBy?.split(",")?.size} users"
        binding.quesTitleTv.text = model.title
        binding.quesDescTv.text = model.ques
        binding.deleteBtn.setOnClickListener {
            activity.deleteAnswer(model.docId.toString())
            Log.d(TAG, "onBindViewHolder: ${model.docId} , ${model.ans}")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)
}