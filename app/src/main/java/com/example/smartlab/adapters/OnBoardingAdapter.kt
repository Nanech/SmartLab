package com.example.smartlab.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smartlab.fragments.OnBoardingFragment
import com.example.smartlab.models.OnBoardingPage

class OnBoardingAdapter (activity: FragmentActivity, private val pagerList: ArrayList<OnBoardingPage>)
    : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return pagerList.size
    }

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment(pagerList[position])
    }


}