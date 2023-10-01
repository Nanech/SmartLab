package com.example.smartlab.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentEmailCodeBinding

class EmailCodeFragment : Fragment() {

    private var _binding: FragmentEmailCodeBinding? = null
    private val  binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)

        val view = binding.root



        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.back_to_email)
        }


        //inflater.inflate(R.layout.fragment_email_code, container, false)

        return view
    }



}