package com.example.kaisebhiadmin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.QuestionsAdapter
import com.example.kaisebhiadmin.databinding.FragmentPendingBinding
import com.example.kaisebhiadmin.models.QuestionsModel

class FragmentPending(private val quesStatus: String) : Fragment() {
    private lateinit var binding: FragmentPendingBinding
//    private lateinit var viewModel: MainViewModel
    private var list = ArrayList<QuestionsModel>()
    private val TAG = "FragQualityCheck.kt"
    private var adapter: QuestionsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending, container, false)
//        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(MainRepository(FirebaseApiCalls((activity?.application as AppCustom)))))[MainViewModel::class.java]
//        binding.model = viewModel
        activity?.let {
            val viewModel = (activity as MainActivity).viewModel
            adapter = QuestionsAdapter(it, viewModel,
                it.supportFragmentManager)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
//            adapter!!.submitList(list)
            Log.d(TAG, "onCreateView: ${list.size}")
        }
        return binding.root
    }

    fun setupList(pagingData: PagingData<QuestionsModel>) {
        adapter?.submitData(lifecycle, pagingData)
        Log.d(TAG, "setObservers success pending: $list")
    }

}