package com.example.kaisebhiadmin.data.localdb

import android.content.Context
import android.content.SharedPreferences

class SessionConfig(private val ctx: Context) {
    private lateinit var sharedPref: SharedPreferences
    init {
        sharedPref = ctx.getSharedPreferences(ctx.packageName.toString(), Context.MODE_PRIVATE)
    }

    fun getLoginStatus(): Boolean {
        return sharedPref.getBoolean("loginStatus", false)
    }

    fun setLoginStatus(status: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("loginStatus", status)
        editor.apply()
    }
}