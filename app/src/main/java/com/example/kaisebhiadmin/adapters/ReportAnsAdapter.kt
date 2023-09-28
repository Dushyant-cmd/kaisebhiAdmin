package com.example.kaisebhiadmin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.databinding.ReportListItemBinding
import com.example.kaisebhiadmin.models.ReportedModel
import com.example.kaisebhiadmin.ui.report.ReportActivity

class ReportAnsAdapter(private val ctx: Context) :
    PagingDataAdapter<ReportedModel, ReportAnsAdapter.ViewHolder>(COMPARATOR) {
    private val TAG: String = "ReportAnsAdapter.kt"
    lateinit var binding: ReportListItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportAnsAdapter.ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.report_list_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportAnsAdapter.ViewHolder, position: Int) {
        val model = getItem(position)
        model?.let {
            holder.bind(model)
        }
    }

    companion object {
        val COMPARATOR = object: DiffUtil.ItemCallback<ReportedModel>() {
            override fun areContentsTheSame(
                oldItem: ReportedModel,
                newItem: ReportedModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: ReportedModel, newItem: ReportedModel): Boolean {
                return oldItem.docId == newItem.docId
            }
        }
    }
    inner class ViewHolder(val binding: ReportListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ReportedModel) {
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
    }
}