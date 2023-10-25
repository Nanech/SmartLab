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

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.analyzes -> {
                    replaceFragment(AnalyzesFragment())
                    true }
                R.id.results ->{
                    replaceFragment(ResultsFragment())
                    true
                }
                R.id.support ->{
                    replaceFragment(SupportFragment())
                    true
                }
                R.id.profile ->{
                    replaceFragment(ProfileFragment())
                    true
                }
                else  -> false
            }
        }

        replaceFragment(AnalyzesFragment())


//        setContentView(R.layout.activity_mediator)

        setContentView(binding.root)

    }


    private  fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }



}
