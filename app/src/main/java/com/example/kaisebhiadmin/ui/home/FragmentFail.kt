package com.example.kaisebhiadmin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.QuestionsAdapter
import com.example.kaisebhiadmin.databinding.FragmentFailBinding
import com.example.kaisebhiadmin.models.QuestionsModel

class FragmentFail(private val quesStatus: String) : Fragment() {
    private lateinit var binding: FragmentFailBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "FragQualityCheck.kt"
    private var list = ArrayList<QuestionsModel>()
    private var adapter: QuestionsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fail, container, false)
//        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(
//            MainRepository(
//                FirebaseApiCalls((activity?.application as AppCustom))
//            )
//        ))[MainViewModel::class.java]
//        binding.model = viewModel

        viewModel = (activity as MainActivity).viewModel
        activity?.let {
            adapter = QuestionsAdapter(list, it, viewModel,
                it.supportFragmentManager)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter!!.submitList(list)
        }
//        viewModel.getQues(quesStatus, 10)
//        setObservers()
        return binding.root
    }

    fun setupList(list2: ArrayList<QuestionsModel>) {
        this.list = list2
        activity?.let {
            Log.d(TAG, "setupList fail: ${list[0].qualityCheck}")
            val viewModel = (activity as MainActivity).viewModel
            adapter = QuestionsAdapter(list, it, viewModel,
                it.supportFragmentManager)
            binding.recyclerView.adapter = adapter
            adapter!!.submitList(list)
            adapter!!.notifyDataSetChanged()
        }
    }

}