package com.example.smartlab.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.databinding.ActivityMainBinding
import com.example.smartlab.databinding.ActivityMediatorBinding
import java.util.zip.Inflater


class MainActivity : AppCompatActivity() {

    // Late init variable for init in onCreate
    private  lateinit var navController: NavController
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        installSplashScreen() // Start the Splash Screen

        // Sets binding as "activitymain.xml" and runs it
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        //  Starts the navigation

        val navHostController = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostController.navController

    }

    override fun onStart() {
        super.onStart()
        isFirstTime() // For OnBoarding Page
    }

    private fun isFirstTime(){
        // Checks whether the OnBoarding was started or not
        val sharedPreferenceManager  = SharedPreferenceManager(this)
        if (sharedPreferenceManager.isFirstTime){
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
    }

}