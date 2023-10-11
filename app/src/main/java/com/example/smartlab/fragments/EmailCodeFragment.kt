package com.example.smartlab.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smartlab.R
import com.example.smartlab.adapters.MyAPI
import com.example.smartlab.databinding.FragmentEmailCodeBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.Locale
import java.util.concurrent.TimeUnit

class EmailCodeFragment : Fragment() {

    private var _binding: FragmentEmailCodeBinding? = null
    private val  binding get() = _binding!!

    private lateinit var timer: CountDownTimer

   val args: EmailCodeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)

        val view = binding.root

        val myEmail = args.email // Taken my email from previous fragment

        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.back_to_email)
        }

        binding.otpView.setOtpCompletionListener {
            hideKeyboard()
            Toast.makeText( requireContext() , "Entered pin: $it", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.to_create_passcode)
        }

        var duration = TimeUnit.MINUTES.toMillis(1);

        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(remaining: Long) {
                var seconds = String.format(Locale.ENGLISH, "%02d", TimeUnit.MILLISECONDS.toSeconds(remaining))
                binding.emailText.text = "Отправить код повторно можно будет через " +
                        seconds + " секунд."
            }

            override fun onFinish() {
               binding.emailText.isClickable = true
               binding.emailText.text = "Нажмите, чтобы отправить код"
               // Здесь код для повторной отправки сообщения после которого emailText.isClickable = false
            }

        }


        //inflater.inflate(R.layout.fragment_email_code, container, false)

        return view
    }

    fun sendEmail(){
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
                requestApi.postEmail(binding.emailText.text.toString())
                    .awaitResponse()
                Log.d("Response", "Success send email")
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString())
            }

        }

    }


    override fun onStart() {
        super.onStart()
        timer.start()
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}