package com.example.smartlab

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.example.smartlab.fragments.CreatePassCodeFragment

class SharedPreferenceManager (context: Context) {


    private val preference = context.getSharedPreferences(
        context.getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE
    )

    private val editor = preference.edit()


    private val keyIsFirstTime = "isFirstTime"



    var isFirstTime
        get() = preference.getBoolean(keyIsFirstTime, true)
        set(value){
            editor.putBoolean(keyIsFirstTime, value)
            editor.commit()
        }


    // Working with Map<String> : String to get and set value
    var passCode
        get() = preference.getString("PASSCODE", "")
        set(value){
            editor.putString("PASSCODE", value)
            editor.apply()
        }



}

