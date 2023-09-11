package com.example.kaisebhiadmin.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.kaisebhiadmin.utils.ResponseError
import com.example.kaisebhiadmin.utils.Success
import com.google.firebase.firestore.DocumentSnapshot

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewMode
    private lateinit var email: String
    private lateinit var pass: String
    private val TAG = "SignInActivity.kt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        viewModel = ViewModelProvider(
            this,
            MainSignViewModelFactory(MainRepository((application as AppCustom).firebaseApiClass))
        )[SignInViewMode::class.java]
        viewModel.getAdminCred()
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.signIn.setOnClickListener(View.OnClickListener {
            val userEmail = binding.email.text.toString()
            val userPass = binding.pass.text.toString()
            if (!userEmail.isNullOrEmpty() && !userPass.isNullOrEmpty()) {
                if (userPass.length > 6) {
                    if (userEmail == email && userPass == pass) {
                        (application as AppCustom).sessionConfig.setLoginStatus(true)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    } else
                        Toast.makeText(this, "Email or Password wrong", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, "Password must be of 6 characters", Toast.LENGTH_SHORT)
                        .show()
            } else
                Toast.makeText(this, "Fill All Details", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setObservers() {
        viewModel.adminLiveData.observe(this, Observer {
            if (it is ResponseError) {
                Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "setObservers: ${it.msg}")
            } else {
                val success = it as Success<DocumentSnapshot>
                success.response?.let {
                    email = success.response.getString("email") as String
                    pass = success.response.getString("password") as String
                }
                Log.d(TAG, "setObservers: ${it.response}")
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
        })
    }
}