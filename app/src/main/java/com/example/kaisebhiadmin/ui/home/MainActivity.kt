package com.example.kaisebhiadmin.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivityMainBinding
import com.example.kaisebhiadmin.ui.login.SignInActivity
import com.example.kaisebhiadmin.utils.AppCustom
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.firebase.firestore.DocumentSnapshot

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
        viewModel.getQues()
        setListeners()
        setObservers()
    }

    private fun setListeners() {
        binding.logOutBtn.setOnClickListener {
            logOut()
        }
    }

    private fun setObservers() {
        viewModel.quesLiveData.observe(this, Observer {
            if(it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setObservers: ${it.msg}")
            } else {
                val success = it as Success<ArrayList<DocumentSnapshot>>
                Log.d(TAG, "setObservers: ${success.response}")
                Toast.makeText(this, "${success.response}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**Below method will log-out user from app */
    fun logOut() {
        application.sessionConfig.clear()
        startActivity(Intent(this, SignInActivity::class.java))
    }
}