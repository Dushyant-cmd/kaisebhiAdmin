package com.example.kaisebhiadmin.utils

import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

val TAG: String = "Utility.kt"

class Utility {
    companion object {
        fun showSnackBar(view: View, msg: String) {
            try {
                val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                snackBar.show()
            } catch (e: Exception) {
                Log.d(TAG, "showSnackBar: $e")
            }
        }
    }
}
