package com.example.smartlab.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentCreatePassCodeBinding
import com.example.smartlab.databinding.FragmentEmailCodeBinding


class CreatePassCodeFragment : Fragment() {


    private var _binding: FragmentCreatePassCodeBinding? = null
    private val binding get() =  _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(  inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {

        _binding = FragmentCreatePassCodeBinding.inflate(inflater, container, false)

        var view = binding.root

        // ломается почему то

//      inflater.inflate(R.layout.fragment_create_pass_code, container, false)

        return view
    }

    fun funOnClick(view: View) {
        binding.apply {
            val text = otpView.text.toString().trim()
            when(view.id){
                in listOf(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9) ->{
                    val number = when(view.id){
                        R.id.btn0 -> "0"
                        R.id.btn1 -> "1"
                        R.id.btn2 -> "2"
                        R.id.btn3 -> "3"
                        R.id.btn4 -> "4"
                        R.id.btn5 -> "5"
                        R.id.btn6 -> "6"
                        R.id.btn7 -> "7"
                        R.id.btn8 -> "8"
                        R.id.btn9 -> "9"
                        else -> ""
                    }
                    binding.otpView.text?.append(number)
                }
                R.id.btnDel -> {
                    if (otpView.text!!.isNotEmpty()){
                        removeLastCharter()
                    }
                }
            }
        }

    }

    private fun removeLastCharter(){
        val text = binding.otpView.text.toString()
        if (text.isNotEmpty()){
            val newText = text.substring(0, text.length-1)
            binding.otpView.setText(newText)
            binding.otpView.setSelection(newText.length)
        }

    }



}