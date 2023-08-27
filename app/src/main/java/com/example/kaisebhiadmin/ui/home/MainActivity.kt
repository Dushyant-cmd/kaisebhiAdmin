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

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    val TAG = "MainActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        viewModel = ViewModelProvider(this@MainActivity,
            MainViewModelFactory(this@MainActivity, MainRepository())).get(MainViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.liveData.observe(this@MainActivity, Observer {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        })
    }
}