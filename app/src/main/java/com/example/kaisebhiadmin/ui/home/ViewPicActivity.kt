package com.example.kaisebhiadmin.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.databinding.ActivityViewPicBinding
import com.squareup.picasso.Picasso

class ViewPicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewPicBinding
    private var picUrl: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_pic)

        intent.getBundleExtra("bundle")?.let {
            picUrl = it.getString("picUrl")
            Picasso.get().load(picUrl).into(binding.fullImage)
        }
    }
}