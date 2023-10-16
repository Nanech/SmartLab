package com.example.smartlab.fragments
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.adapters.MyAPI
import com.example.smartlab.databinding.FragmentProfileCardCreateBinding
import com.example.smartlab.models.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.internal.checkOffsetAndCount
import retrofit2.Retrofit
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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


        // For mouths
//        val pattern = """^\d{1,2} (?:|January|February|March|April|May|June|July|August|September|October|November|December) \d{4}$""".toRegex()

        forTextChanged()


        binding.btn.setOnClickListener {
            sendCreateCabinet()
//            findNavController().navigate()
        }





//        inflater.inflate(R.layout.fragment_profile_card_create, container, false)


        return view
    }

    private fun sendCreateCabinet(){

        var myData =  Profile(0, binding.name.text.toString(),
            binding.surname.text.toString(), binding.middleName.text.toString(),
            binding.datePickerActions.text.toString(), binding.autoComplete.text.toString())

        val sharedPreferenceManager = SharedPreferenceManager(requireContext())

        val header = "Bearer " + sharedPreferenceManager.jwt

        Toast.makeText(requireContext(), "$header", Toast.LENGTH_LONG).show()


        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_root))
            .build()

        val api = retrofit.create(MyAPI::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response = api.postCreateProfile(header, myData)

            }
            catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.d("Send Cabinet Data went wrong:", e.toString())
                }
            }

        }

    }


    private fun getMyDate():Date{
        val date = binding.datePickerActions.text.toString()
        val dateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
        var dateObj = dateFormat.parse(date)
        Toast.makeText(requireContext(), "$dateObj", Toast.LENGTH_LONG)
        return  dateObj
    }


    private fun checkForAllFields(){
        val patternOFName = "^[A-ZА-Я][A-Za-zА-Яа-я]+$".toRegex()

        if ( patternOFName.matches(binding.name.text.trim()) &&
            patternOFName.matches(binding.surname.text.trim()) &&
            patternOFName.matches(binding.middleName.text.trim()) &&
            binding.autoComplete.text.isNotEmpty() && binding.datePickerActions.text.isNotEmpty()){
            binding.btn.isEnabled = true
        } else{ binding.btn.isEnabled = false  }
    }

    private fun forTextChanged(){

        binding.name.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun afterTextChanged(p0: Editable?) {
                checkForAllFields()  }
        })

        binding.surname.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun afterTextChanged(p0: Editable?) {
                checkForAllFields()  }
        })

        binding.middleName.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun afterTextChanged(p0: Editable?) {
                checkForAllFields() }
        } )

        binding.datePickerActions.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun afterTextChanged(p0: Editable?) {
                checkForAllFields() }
        } )

        binding.autoComplete.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            override fun afterTextChanged(p0: Editable?) {
                checkForAllFields()
            }
        } )



    }


    private fun updateLabel(calender: Calendar){
        val dateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
        val sdf = dateFormat
        binding.datePickerActions.setText(sdf.format(calender.time))
    }


}