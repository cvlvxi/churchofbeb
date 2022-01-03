package com.cvlvxi.layouttesting

import android.content.Context
import android.widget.Toast

sealed class Utils {
    fun showToast(text: String, applicationContext: Context) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
}