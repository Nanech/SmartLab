package com.example.smartlab.fragments

import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.api.MyAPI
import com.example.smartlab.databinding.FragmentAuthBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception


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

        val sharedPreferenceManager  = SharedPreferenceManager(requireContext())

        if ( !sharedPreferenceManager.jwt.isNullOrEmpty() ){
            findNavController().navigate(R.id.profileCardCreateFragment)
        }

        emailFocusListener() // Listener for email validation

        binding.btn.setOnClickListener(){
            sendEmail(binding.edEmail.text.toString())

            val action = AuthFragmentDirections.goToEmailCode(binding.edEmail.text.toString().trim())

            findNavController().navigate(action)
        }

        return view
    }

    private fun emailFocusListener(){
        binding.edEmail.addTextChangedListener( object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                if ( android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edEmail.text.
                    toString()).matches()) // Email RegEx
                { binding.btn.isEnabled = true }
                else{
                    binding.btn.isEnabled = false
                    binding.edEmail.setError("Invalid email")
                }
            }
        } )
    }

    fun sendEmail(email: String){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val gsonBuilder = GsonBuilder()
            .setLenient().create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(getString(R.string.api_root))
            .client(httpClient).build()


        val requestApi = retrofit.create(MyAPI::class.java )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                requestApi.postEmail(email)
                    .awaitResponse()
                Log.d("Response", "Success send email")
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString())
            }
        }
    }


}