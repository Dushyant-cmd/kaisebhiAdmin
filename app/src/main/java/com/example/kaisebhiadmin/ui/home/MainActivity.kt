package com.example.kaisebhiadmin.ui.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityMainBinding
import com.example.kaisebhiadmin.utils.AppCustom

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    lateinit var application: AppCustom
    val TAG = "MainActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        application = getApplication() as AppCustom
        viewModel = ViewModelProvider(this@MainActivity,
            MainViewModelFactory(this@MainActivity, MainRepository(application.firebaseApiClass)))[MainViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.getQues();
        setObservers()
    }

    private fun setObservers() {
        viewModel.liveData.observe(this, Observer {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        })
    }
}