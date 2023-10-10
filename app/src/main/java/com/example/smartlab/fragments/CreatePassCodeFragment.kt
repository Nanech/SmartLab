package com.example.smartlab.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentCreatePassCodeBinding
import java.util.concurrent.TimeUnit


class CreatePassCodeFragment : Fragment() {


    private var _binding: FragmentCreatePassCodeBinding? = null
    private val binding get() =  _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {

        _binding = FragmentCreatePassCodeBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.btn0.setOnClickListener(::hadleButtonClick)
        binding.btn1.setOnClickListener(::hadleButtonClick )
        binding.btn2.setOnClickListener(::hadleButtonClick )
        binding.btn3.setOnClickListener(::hadleButtonClick )
        binding.btn4.setOnClickListener(::hadleButtonClick )
        binding.btn5.setOnClickListener(::hadleButtonClick )
        binding.btn6.setOnClickListener(::hadleButtonClick )
        binding.btn7.setOnClickListener(::hadleButtonClick )
        binding.btn8.setOnClickListener(::hadleButtonClick )
        binding.btn9.setOnClickListener(::hadleButtonClick )
        binding.btnDel.setOnClickListener { removeLastCharter() }

        binding.passcodeView.setOtpCompletionListener {
            Toast.makeText(requireContext(), binding.passcodeView.text, Toast.LENGTH_LONG ).show()
            // Need to add to Preference Manager
        }

        binding.Skip.setOnClickListener {
            findNavController().navigate(R.id.action_createPassCodeFragment_to_profileCardCreateFragment)
        }


//      inflater.inflate(R.layout.fragment_create_pass_code, container, false)

        return view
    }



    private fun removeLastCharter(){
        val text = binding.passcodeView.text.toString()
        if (text.isNotEmpty()){
            val newText = text.substring(0, text.length-1)
            binding.passcodeView.setText(newText)
            binding.passcodeView.setSelection(newText.length)
        }
    }


    private fun hadleButtonClick(view: View){

        with(view as Button){
            var numb = "$text"
            writeIntoEdtTxt(numb)
        }

    }

    fun writeIntoEdtTxt(number: String ){
        binding.passcodeView.text?.append(number)
    }




}