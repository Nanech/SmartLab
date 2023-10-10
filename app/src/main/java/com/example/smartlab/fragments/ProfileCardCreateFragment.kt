package com.example.smartlab.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentProfileCardCreateBinding


class ProfileCardCreateFragment : Fragment() {


    private var _binding: FragmentProfileCardCreateBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileCardCreateBinding.inflate(inflater, container, false)

        val view = binding.root


//        inflater.inflate(R.layout.fragment_profile_card_create, container, false)




        val genders = resources.getStringArray(R.array.gender)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoComplete.setAdapter(arrayAdapter)

        // Зачем то он пихает hint хотя я ему анимации обрубил и вот здесь делаю

        binding.autoComplete.setOnItemClickListener { adapterView, view, i, l ->
            var textFromHint = binding.inptLayout.hint

            if( textFromHint != null ) {
                binding.inptLayout.hint  = null
            } else{
                binding.inptLayout.hint  =  "Пол"
            }
        }

        binding.autoComplete.setOnClickListener{

            var textFromHint = binding.inptLayout.hint

            if( textFromHint != null ) {
                binding.inptLayout.hint  = null
            } else{
                binding.inptLayout.hint  =  "Пол"
            }

        }




        return view
    }


}