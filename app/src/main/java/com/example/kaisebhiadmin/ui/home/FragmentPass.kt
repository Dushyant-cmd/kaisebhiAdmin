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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.QuestionsAdapter
import com.example.kaisebhiadmin.databinding.FragmentPassBinding
import kotlinx.coroutines.launch

class FragmentPass(private val quesStatus: String) : Fragment() {
    private lateinit var binding: FragmentPassBinding
    private var adapter: QuestionsAdapter? = null
    private val TAG = "FragmentPass.kt"
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pass, container, false)
        viewModel = (activity as MainActivity).viewModel
        activity?.let {
            adapter = QuestionsAdapter(it, viewModel,
                it.supportFragmentManager)
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        }

        setupList()
        Log.d(TAG, "onCreateView: fail $adapter")
        return binding.root
    }

    fun setupList() {
        lifecycleScope.launch {
        viewModel.getPassQues("pass", 10)
            .observe(viewLifecycleOwner, Observer {
                it?.let {
//                    binding.swipeRef.isRefreshing = false
//                    binding.shimmer.stopShimmerAnimation()
//                    binding.shimmer.visibility = View.GONE
//                    binding.viewPagerLL.visibility = View.VISIBLE
//                    passFragment.setupList(it)
                    adapter?.submitData(lifecycle, it)
                }
                Log.d(TAG, "getData: $it")
            })
    }
        Log.d(TAG, "setObservers success pass: ")
    }
}