package com.example.smartlab.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get()  = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View? {

        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        val view = binding.root

        emailFocusListener()

        binding.btn.setBackgroundResource(R.drawable.btn_nonenabled)

        return view
    }

    private fun emailFocusListener(){
        binding.edEmail.addTextChangedListener( object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if ( android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text.
                    toString()).matches()  ){
                    binding.btn.isEnabled = true
                    binding.btn.setBackgroundResource(R.drawable.btn_enabled)
                } else{
                    binding.btn.isEnabled = false
                    binding.btn.setBackgroundResource(R.drawable.btn_nonenabled)
                    binding.edEmail.setError("Invalid email")
                }
            }

        } )


    }


}