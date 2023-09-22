package com.example.kaisebhiadmin.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.adapters.MainViewPagerAdapter
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityMainBinding
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.ui.login.SignInActivity
import com.example.kaisebhiadmin.ui.report.ReportActivity
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var application: AppCustom
    val TAG = "MainActivity.kt"
    private lateinit var pendingFragment: FragmentPending
    private lateinit var failFragment: FragmentFail
    private lateinit var passFragment: FragmentPass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        application = getApplication() as AppCustom
        viewModel = ViewModelProvider(this@MainActivity,
            MainViewModelFactory(MainRepository(application.firebaseApiClass)))[MainViewModel::class.java]
        binding.viewModel = viewModel
        //Setup Navigation Drawer make toggle and sync state with drawer then
        //add listener on drawer with toggle.
        val toggle = ActionBarDrawerToggle(this@MainActivity, binding.drawerLayout, binding.toolbar, 0, 0)
        toggle.syncState()
        binding.drawerLayout.addDrawerListener(toggle)
        pendingFragment = FragmentPending("pending")
        failFragment = FragmentFail("fail")
        passFragment = FragmentPass("pass")
        getData()
        //setup ViewPager
        val pagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        pagerAdapter.addFragmentAndTitle(pendingFragment, "QC Pending")
        pagerAdapter.addFragmentAndTitle(failFragment, "QC Fail")
        pagerAdapter.addFragmentAndTitle(passFragment, "QC Pass")
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        setListeners()
        setObservers()
    }

    /**Below method call all the api requests. */
    fun getData() {
        //Api call for all question based on filter
        viewModel.getFailQues("fail", 10)
        viewModel.getPendingQues("pending", 10)
        viewModel.getPassQues("pass", 10)
    }

    private fun setListeners() {
        binding.swipeRef.setOnRefreshListener {
            binding.swipeRef.isRefreshing = true
            getData()
        }
        binding.navigationMenu.setNavigationItemSelectedListener(object: NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when(item.itemId) {
                    R.id.reportAns -> {
                        startActivity(Intent(this@MainActivity, ReportActivity::class.java))
                        true
                    }

                    R.id.allQues -> {
                        binding.drawerLayout.close()
                        true
                    }

                    R.id.logOut -> {
                        val dialog = AlertDialog.Builder(this@MainActivity)
                        dialog.setMessage("Are you sure to log-out")
                        dialog.setPositiveButton("Yes"
                        ) { p0, p1 -> logOut() }

                        dialog.setNegativeButton("No"
                        ) { dialogInterface, p1 ->
                            //cancel
                        }
                        dialog.show()
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        })
    }

    private fun setObservers() {
        viewModel.pendingQuesLiveData.observe(this, Observer {
            if(it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "setObservers: ${it.msg}")
            } else {
                val success = it as Success<ArrayList<QuestionsModel>>
                binding.swipeRef.isRefreshing = false
                pendingFragment.setupList(success.response)
                Log.d(TAG, "setObservers pending: ${success.response}")
            }
        })
        viewModel.failQuesLiveData.observe(this, Observer {
            if(it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setObservers: ${it.msg}")
            } else {
                val success = it as Success<ArrayList<QuestionsModel>>
                binding.swipeRef.isRefreshing = false
                failFragment.setupList(success.response)
                Log.d(TAG, "setObservers: ${success.response}")
            }
        })
        viewModel.passQuesLiveData.observe(this, Observer {
            if(it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                binding.swipeRef.isRefreshing = false
                Log.d(TAG, "setObservers: ${it.msg}")
            } else {
                binding.swipeRef.isRefreshing = false
                val success = it as Success<ArrayList<QuestionsModel>>
                binding.swipeRef.isRefreshing = false
                passFragment.setupList(success.response)
                Log.d(TAG, "setObservers pass: ${success.response}")
            }
        })

        viewModel.updateLiveData.observe(this@MainActivity, Observer {
            if(it is Success<*>) {
//                val map = it.response as Map<*, *>
//                when(map.get("status")) {
//                    "fail" -> viewModel.getFailQues("fail", 10)
//                    "pass" -> viewModel.getPassQues("pass", 10)
//                }
//
//                when(map.get("qualityCheck")) {
//                    "fail" -> viewModel.getFailQues("fail", 10)
//                    "pass" -> viewModel.getPassQues("pass", 10)
//                    "pending" -> viewModel.getPendingQues("pass", 10)
//                }
                binding.swipeRef.isRefreshing = true
                getData()
                Toast.makeText(this@MainActivity, it.response.toString(), Toast.LENGTH_SHORT).show()
                Log.d(TAG, "setObservers: ${it.response}")
            } else if(it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, "setObservers: ${it.msg}")
            }
        })
    }

    /**Below method will log-out user from app */
    fun logOut() {
        application.sessionConfig.clear()
        startActivity(Intent(this, SignInActivity::class.java))
    }

    /**Below method will notify list about item. */
//    fun notifyList(pos: String) {
//
//    }
}