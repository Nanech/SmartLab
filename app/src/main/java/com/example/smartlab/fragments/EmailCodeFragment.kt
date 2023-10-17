package com.example.smartlab.fragments

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.smartlab.R
import com.example.smartlab.SharedPreferenceManager
import com.example.smartlab.api.MyAPI
import com.example.smartlab.databinding.FragmentEmailCodeBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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

            sendEmailAndCode(myEmail, it)
        }

        var duration = TimeUnit.MINUTES.toMillis(1);

        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(remaining: Long) {
                var seconds = String.format(Locale.ENGLISH, "%d", TimeUnit.MILLISECONDS.toSeconds(remaining))
                binding.emailText.text = "Отправить код повторно можно будет через " +
                        seconds + " секунд."
            }

            override fun onFinish() {
               binding.emailText.isClickable = true
               binding.emailText.setTextColor(Color.parseColor("#57A9FF"))
               binding.emailText.text = "Нажмите, чтобы повторно отправить код"
               // May here trouble
               binding.emailText.setOnClickListener {
                   sendEmail(myEmail)
                   binding.emailText.setTextColor(Color.parseColor("#7B7B7D"))
                   binding.emailText.isClickable = false
                   timer.start()
               }
            }

        }

        //inflater.inflate(R.layout.fragment_email_code, container, false)

        return view
    }

    private fun createDialog(myEmail: String){

        // Wired realization needs to add binding. But idk how do it

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val title: TextView = dialog.findViewById(R.id.title_dialog)
        val message: TextView = dialog.findViewById(R.id.message_dialog)
        val btnYes: Button = dialog.findViewById(R.id.btn_yes)
        val btnNo: Button = dialog.findViewById(R.id.btn_no)

        title.text = "Ошибка авторизации"
        message.text = "Возможно ошибка в вашей почте \"$myEmail\".\n" +
                "\nЕсли это ваша почта, нажмите правую кнопку и " +
                "повторите ввод кода, иначе нажмите левую кнопку"
        btnNo.text = "Окей"
        btnYes.text = "Изменить почту"

       btnYes.setOnClickListener {
            findNavController().navigate(R.id.back_to_email)
           dialog.dismiss()
           dialog.cancel()
        }

        btnNo.setOnClickListener {
            binding.otpView.text = null
            dialog.dismiss()
            dialog.cancel()
        }

        dialog.show()
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
                requestApi.postSignIn(email, binding.otpView.text.toString())
                    .awaitResponse()
                Log.d("Response", "Success send email")
            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString())
            }

        }
    }

    fun sendEmailAndCode(myEmail: String, code: String) {

        val interceptor = HttpLoggingInterceptor()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_root))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val api = retrofit.create(MyAPI::class.java )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = api.postSignIn(myEmail, code ).awaitResponse()
                val shrdPrefManag = SharedPreferenceManager(requireContext())

                if (response.isSuccessful){// JWT is taken
                    val data = response.body()!!

                    Log.d("Gained JWT", data.toString() )
                    shrdPrefManag.jwt = data

                    if (!shrdPrefManag.jwt.isNullOrEmpty()){
                        findNavController().navigate(R.id.to_create_passcode)
                    }

                }
                else{// JWT is not gained
                    Log.d("Gained JWT", "Something went wrong" )
                    shrdPrefManag.jwt = ""

                    createDialog(myEmail)
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Log.d("ThisWentWrong", e.toString())
                }
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

    fun Fragment.hideKeyboard() { view?.let { activity?.hideKeyboard(it) } }

    fun Activity.hideKeyboard() { hideKeyboard(currentFocus ?: View(this))   }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}