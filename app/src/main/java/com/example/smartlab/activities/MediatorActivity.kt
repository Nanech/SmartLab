package com.example.smartlab.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.smartlab.R
import com.example.smartlab.databinding.ActivityMediatorBinding
import com.example.smartlab.fragments.AnalyzesFragment
import com.example.smartlab.fragments.ProfileFragment
import com.example.smartlab.fragments.ResultsFragment
import com.example.smartlab.fragments.SupportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MediatorActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMediatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediatorBinding.inflate(layoutInflater)


        bottomNavigationView = binding.bottomNavigation



//        replaceFragment(AnalyzesFragment())


//        setContentView(R.layout.activity_mediator)

        setContentView(binding.root)

    }





}
