package com.example.smartlab.fragments
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isNotEmpty
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentProfileCardCreateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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

        val genders = resources.getStringArray(R.array.gender)
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.autoComplete.setAdapter(arrayAdapter)

        // Немного некорректно но сойдёт

        binding.autoComplete.setOnClickListener{
            var textFromHint = binding.inptLayout.hint

            if( textFromHint != null || binding.autoComplete.text.isNotEmpty()  ) {
                binding.inptLayout.hint  = null
            } else{
                binding.inptLayout.hint  =  "Пол"
            }
        }

        val calender = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->

            calender.set(Calendar.YEAR, year)
            calender.set(Calendar.MONTH, month)
            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(calender)
        }

        binding.datePickerActions.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show()
        }


//        val pattern = """^\d{1,2} (?:|January|February|March|April|May|June|July|August|September|October|November|December) \d{4}$""".toRegex()

        var checkAllField: Boolean = false

        binding.name.doAfterTextChanged {checkAllField = checkForAllFields() }
        binding.surname.doAfterTextChanged {checkAllField = checkForAllFields() }
        binding.middleName.doAfterTextChanged {checkAllField = checkForAllFields() }
        binding.autoComplete.doAfterTextChanged {checkAllField = checkForAllFields() }
        binding.datePickerActions.doAfterTextChanged {checkAllField = checkForAllFields() }



        inflater.inflate(R.layout.fragment_profile_card_create, container, false)

        if (checkAllField == true){
            binding.btn.isEnabled = true
        } else { binding.btn.isEnabled = false  }

        return view
    }

    private fun checkForAllFields():Boolean{

        val patternOFName = "^[A-ZА-Я][A-Za-zА-Яа-я]+$".toRegex()

        if ( patternOFName.matches(binding.name.text.trim()) &&
            patternOFName.matches(binding.surname.text.trim()) &&
            patternOFName.matches(binding.middleName.text.trim()) &&
            binding.autoComplete.text.isNotEmpty() && binding.datePickerActions.text.isNotEmpty()){
            return  true
        }


        return false;
    }



    private fun updateLabel(calender: Calendar) {
        val dateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
        val sdf = dateFormat
        binding.datePickerActions.setText(sdf.format(calender.time))
    }


}