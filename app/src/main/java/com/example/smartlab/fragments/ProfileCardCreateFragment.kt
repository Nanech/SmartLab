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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.ViewModel
import com.example.smartlab.ViewModelFactory
import com.example.smartlab.api.MyAPI
import com.example.smartlab.databinding.FragmentProfileCardCreateBinding
import com.example.smartlab.models.Profile
import com.example.smartlab.repository.Repository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ProfileCardCreateFragment : Fragment() {


    private var _binding: FragmentProfileCardCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModel

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

        val myDate = getMyDate()


        val myData =  Profile(0, binding.name.text.toString(),
            binding.surname.text.toString(), binding.middleName.text.toString(),
            myDate, binding.autoComplete.text.toString())

        val sharedPreferenceManager = SharedPreferenceManager(requireContext())

        val header = "Bearer " + sharedPreferenceManager.jwt

//        val repository = Repository()
//        val viewModelFactory = ViewModelFactory(repository)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(ViewModel::class.java)
//
//        viewModel.postCreateProfile(header, myData)
//
//        viewModel.myResponseProfile.observe(viewLifecycleOwner, Observer { response ->
//            try {
//                if (response.isSuccessful){
//                    Log.d("Response Body", response.body().toString() )
//                    Log.d("Response Code", response.code().toString() )
//                    Log.d("Response Message", response.message().toString() )
//                }else{
//                    Toast.makeText(requireContext(), "${response.code()}", Toast.LENGTH_LONG).show()
//                    Log.d("Response error", response.code().toString())
//                }
//            }catch (e: Exception){
//                Log.d("Error msg", e.message.toString())
//            }
//        })


        val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(getString(R.string.api_root))
            .client(httpClient)
            .build()



        val api = retrofit.create(MyAPI::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.postCreateProfile(header, myData)

                if (response.isSuccessful){
                    Log.d("Respond code", response.code().toString())
                    Log.d("Post respond", response.body().toString())
                    Log.d("Respond message", response.message().toString())
                }else{
                    Log.d("Respond error code", response.code().toString())
                    Log.d("Respond body", response.body().toString())
                    Log.d("Respond error body", response.errorBody().toString())
                }

            }
            catch (e: Exception){
                withContext(Dispatchers.Main){

                    Log.d("Something went wrong", e.toString())
                }
            }

        }

    }


    private fun getMyDate():String{
        val date = binding.datePickerActions.text.toString()
        val dateFormat = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
        var dateObj = dateFormat.parse(date)
        val newDateFormant = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val myString = newDateFormant.format(dateObj.time)
        return  myString
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