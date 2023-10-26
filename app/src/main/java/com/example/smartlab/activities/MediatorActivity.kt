package com.example.smartlab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.ActivityMediatorBinding
import com.example.smartlab.fragments.AnalyzesFragment
import com.example.smartlab.fragments.ProfileFragment
import com.example.smartlab.fragments.ResultsFragment
import com.example.smartlab.fragments.SupportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MediatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediatorBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostController = supportFragmentManager
            .findFragmentById(R.id.fragment_container_mediator) as NavHostFragment
        val navController = navHostController.navController

        binding.bottomNavigation.setupWithNavController(navController)


//        setContentView(R.layout.activity_mediator)


    }





}
