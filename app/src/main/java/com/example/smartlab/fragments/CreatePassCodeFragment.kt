package com.example.smartlab.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentCreatePassCodeBinding


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

        binding.btn0.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn1.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn2.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn3.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn4.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn5.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn6.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn7.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn8.setOnClickListener{ view -> hadleButtonClick(view) }
        binding.btn9.setOnClickListener{ view -> hadleButtonClick(view) }
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


    fun hadleButtonClick(view: View){
        with(view as Button){
            var numb = "$text"
            writeIntoEdtTxt(numb)
        }
    }

    fun writeIntoEdtTxt(number: String ){
        binding.passcodeView.text?.append(number)
    }




}