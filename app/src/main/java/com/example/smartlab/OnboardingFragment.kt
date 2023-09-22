package com.example.smartlab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.smartlab.databinding.FragmentOnboardingBinding
import com.example.smartlab.models.OnBoardingData


class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    private val binding get()  = _binding!!

    lateinit var onBoardingVP2: ViewPager2

    private val pagerList = arrayListOf(
        OnBoardingData(R.raw.analyzes, getString(R.string.header1) , getString(R.string.desc1)),
        OnBoardingData(R.raw.notifications, getString(R.string.header2) , getString(R.string.desc2)),
        OnBoardingData(R.raw.monitoring, getString(R.string.header3) , getString(R.string.desc3))
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? ): View? {

        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        val view  = binding.root

        onBoardingVP2 = binding.switcher

        onBoardingVP2.apply {
//            adapter

            // https://www.youtube.com/watch?v=PZp9_tfF6uE&t=289s
            // https://www.youtube.com/watch?v=5p59XpDUKhg&t=761s

            // We stopped here, need to make adapter class and them functions
            // Needs to do some navigation and add swipes
            // We`re got here list of the our future pages and more

        }


        return view
    }

}