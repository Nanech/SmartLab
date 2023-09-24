package com.example.smartlab.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentOnBoardingBinding
import com.example.smartlab.models.OnBoardingPage


class OnBoardingFragment (val page: OnBoardingPage) : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        val view = binding.root

//       val view =  inflater.inflate(R.layout.fragment_on_boarding, container, false)


        binding.headerTitle.text = page.header
        binding.decsTitle.text = page.description
        binding.ltAnimations.setAnimation(page.animation)

        return view
    }


}