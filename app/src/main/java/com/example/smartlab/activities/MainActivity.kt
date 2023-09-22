package com.example.smartlab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.smartlab.AuthFragment
import com.example.smartlab.R
import com.example.smartlab.databinding.ActivityMainBinding
import javax.net.ssl.SSLSessionBindingEvent


class MainActivity : AppCompatActivity() {

    // Late init variable for init in onCreate
    private  lateinit var navController: NavController
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Sets binding as "activitymain.xml" and runs it
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)


        // Starts the navigation

        val navHostController = supportFragmentManager
            .findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostController.navController

    }
}