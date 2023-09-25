package com.example.smartlab.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.adapters.OnBoardingAdapter
import com.example.smartlab.databinding.ActivityOnBoardingBinding
import com.example.smartlab.models.OnBoardingPage
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardingActivity : AppCompatActivity() {


    private lateinit var pagerList:ArrayList<OnBoardingPage>



    lateinit var binding: ActivityOnBoardingBinding


    private val onBoardingPageChangerCallback  = object :  ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            when (position) {
                0 -> {binding.Skip.text = getString(R.string.skip_txt)}
                1 -> {binding.Skip.text = getString(R.string.skip_txt)}
                2 -> {binding.Skip.text = getString(R.string.ends_txt)}
                else -> {binding.Skip.text = getString(R.string.ends_txt) }
            }


        }
    }


    lateinit var onBoardingViewPage2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root

        pagerList = arrayListOf(
        OnBoardingPage(getString(R.string.header1), getString(R.string.desc1), R.raw.analyzes ),
        OnBoardingPage(getString(R.string.header2), getString(R.string.desc2), R.raw.notifications ),
        OnBoardingPage(getString(R.string.header3), getString(R.string.desc3), R.raw.monitoring)
        )
        onBoardingViewPage2 = binding.switcher

        onBoardingViewPage2.apply {
            adapter = OnBoardingAdapter(this@OnBoardingActivity,pagerList)
            registerOnPageChangeCallback(onBoardingPageChangerCallback)
            (getChildAt(0) as RecyclerView ).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val tablLayout = binding.tabLayout
        TabLayoutMediator(tablLayout, onBoardingViewPage2){tab, position -> }.attach()

        binding.Skip.setOnClickListener {
            if (onBoardingViewPage2.currentItem < onBoardingViewPage2.adapter!!.itemCount-1){
                onBoardingViewPage2.currentItem += 1
            } else if (binding.Skip.text ==  getString(R.string.ends_txt) ) {
                homeScreenIntent()
            }
        }

//        setContentView(R.layout.activity_on_boarding)
        setContentView(view)
    }

    override fun onDestroy() {
        onBoardingViewPage2.unregisterOnPageChangeCallback(onBoardingPageChangerCallback)
        super.onDestroy()
    }

    private fun homeScreenIntent() {
        val sharedPreferenceManager = SharedPreferenceManager(this)
        sharedPreferenceManager.isFirstTime = false
        val homeIntent = Intent(this, MainActivity::class.java)
        startActivity(homeIntent)

    }
}