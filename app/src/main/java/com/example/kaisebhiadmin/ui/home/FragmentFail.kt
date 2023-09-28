package com.example.kaisebhiadmin.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.QuestionsAdapter
import com.example.kaisebhiadmin.databinding.FragmentFailBinding
import com.example.kaisebhiadmin.models.QuestionsModel
import kotlinx.coroutines.launch

class FragmentFail(private val quesStatus: String) : Fragment() {
    private lateinit var binding: FragmentFailBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = "FragmentFail.kt"
    private var pagingData: PagingData<QuestionsModel>? = null
    private var adapter: QuestionsAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fail, container, false)

        viewModel = (activity as MainActivity).viewModel
        activity?.let {
            adapter = QuestionsAdapter(it, viewModel,
                it.supportFragmentManager)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(it)
        }

        setupList()
        Log.d(TAG, "onCreateView: fail $adapter")
        return binding.root
    }

    fun setupList() {
        lifecycleScope.launch {
            viewModel.getFailQues("fail", 10)
                .observe(viewLifecycleOwner, Observer {
                    it?.let {
//                        binding.swipeRef.isRefreshing = false
//                        binding.shimmer.stopShimmerAnimation()
//                        binding.shimmer.visibility = View.GONE
//                        binding.viewPagerLL.visibility = View.VISIBLE
//                        failFragment.setupList(it)
                        adapter?.submitData(lifecycle, it)
                    }
                    Log.d(TAG, "getData: $it")
                })
        }
    }

}