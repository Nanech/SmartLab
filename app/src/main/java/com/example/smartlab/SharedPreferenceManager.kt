package com.example.smartlab

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class SharedPreferenceManager (context: Context) {

    private val preference = context.getSharedPreferences(
        context.getString(R.string.app_name), AppCompatActivity.MODE_PRIVATE
    )

    private val editor = preference.edit()


    private val keyIsFirstTime = "isFirstTime"

    private val email = ""

    var setEmail
        get() = preference.getString("", email)
        set(value) {
            editor.putString(email, value)
            editor.commit()
        }


    var isFirstTime
        get() = preference.getBoolean(keyIsFirstTime, true)
        set(value){
            editor.putBoolean(keyIsFirstTime, value)
            editor.commit()
        }


}