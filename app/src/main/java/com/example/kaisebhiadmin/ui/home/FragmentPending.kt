package com.example.kaisebhiadmin.ui.home

import android.content.Intent
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
import com.example.kaisebhiadmin.databinding.FragmentPendingBinding
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.utils.QuestionClickListener
import kotlinx.coroutines.launch

class FragmentPending(private val quesStatus: String) : Fragment() {
    private lateinit var binding: FragmentPendingBinding

    //    private lateinit var viewModel: MainViewModel
    private var list = ArrayList<QuestionsModel>()
    private val TAG = "FragmentPending.kt"
    private var adapter: QuestionsAdapter? = null
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending, container, false)

        viewModel = (activity as MainActivity).viewModel
        activity?.let {
            adapter = QuestionsAdapter(
                it, viewModel,
                it.supportFragmentManager
            )
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)

            adapter!!.addClickListener(object: QuestionClickListener {
                override fun onClick(picUrl: String?) {
                    activity?.let { activity ->
                        val intent = Intent(activity, ViewPicActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("picUrl", picUrl)
                        intent.putExtra("bundle", bundle)
                        startActivity(intent)
                    }
                }
            })
        }
        Log.d(TAG, "onCreateView: pending $adapter")
        setupList()
        return binding.root
    }

    fun setupList() {
        lifecycleScope.launch {
            viewModel.getPendingQues("pending", 10)
                .observe(viewLifecycleOwner, Observer {
                    it?.let {
                        adapter?.submitData(lifecycle, it)
                    }
                    Log.d(TAG, "getData: $it")
                })
        }
        Log.d(TAG, "setObservers success pending: ${adapter?.itemCount}")
    }

}