package com.example.hackathon.utility

import android.content.Context
import android.widget.Toast

object ViewUtility {

    fun showToast(content:String, context:Context){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }
}