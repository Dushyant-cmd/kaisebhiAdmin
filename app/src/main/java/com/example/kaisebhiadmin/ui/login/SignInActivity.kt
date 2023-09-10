package com.example.kaisebhiadmin.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.data.MainRepository
import com.example.kaisebhiadmin.databinding.ActivitySignInBinding
import com.example.kaisebhiadmin.ui.home.MainActivity
import com.example.kaisebhiadmin.utils.AppCustom

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewMode
    private val TAG = "SignInActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProvider(this, MainSignViewModelFactory(MainRepository((application as AppCustom).firebaseApiClass)))[SignInViewMode::class.java]
        viewModel.getAdminCred()
        setObservers()
    }

    private fun setObservers() {
        viewModel.adminLiveData.observe(this, Observer {
//            val email = it.getString("email")
//            val pass = it.getString("password")
            val userEmail = binding.email.text.toString()
            val userPass = binding.pass.text.toString()
            val email = "admin@gmail.com"
            val pass = "Admin@123"
            binding.signIn.setOnClickListener(View.OnClickListener {
                if(!userEmail.isNullOrEmpty() && !userPass.isNullOrEmpty()) {
                    if(userPass.length > 6) {
                        if(userEmail == email && userPass == pass) {
                            (application as AppCustom).sessionConfig.setLoginStatus(true)
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        } else
                            Toast.makeText(this, "Email or Password wrong", Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(this, "Password must be of 6 characters", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, "Fill All Details", Toast.LENGTH_SHORT).show()
            })
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        })
    }
}