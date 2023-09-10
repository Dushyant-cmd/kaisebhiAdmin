package com.example.kaisebhiadmin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.localdb.SessionConfig
import com.example.kaisebhiadmin.databinding.ActivitySplashBinding
import com.example.kaisebhiadmin.ui.home.MainActivity
import com.example.kaisebhiadmin.ui.login.SignInActivity
import com.example.kaisebhiadmin.utils.AppCustom
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var sessionConfig: SessionConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        sessionConfig = (application as AppCustom).sessionConfig
        lifecycleScope.launch {
            delay(2000)
            when(sessionConfig.getLoginStatus()) {
                true -> {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                false -> {
                    startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                }
            }
        }
    }
}