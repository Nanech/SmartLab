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

//        binding.apply {
//            val text = passcodeView.text.toString().trim()
//            when (view.id) {
//                in listOf(
//                    R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
//                    R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
//                ) -> {
//                    val number = when (view.id) {
//                        binding.btn0.id -> "0"
//                        binding.btn1.id-> "1"
//                        binding.btn2.id -> "2"
//                        binding.btn3.id -> "3"
//                        binding.btn4.id -> "4"
//                        binding.btn5.id -> "5"
//                        binding.btn6.id -> "6"
//                        binding.btn7.id -> "7"
//                        binding.btn8.id -> "8"
//                        binding.btn9.id -> "9"
//                        else -> ""
//                    }
//                    binding.passcodeView.text?.append(number)
//                }
//
//                R.id.btnDel -> {
//                    if (passcodeView.text!!.isNotEmpty()) {
//                        removeLastCharter()
//                    }
//                }
//            }
//        }

        with(view as Button){
            var btn = view.id
            var numb = "$text"
            writeIntoEdtTxt(numb)
        }
    }

    fun writeIntoEdtTxt(number: String ){
        binding.passcodeView.text?.append(number)
    }




}