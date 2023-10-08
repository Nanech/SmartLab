package com.example.smartlab.fragments

import android.app.Activity
import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentEmailCodeBinding
import java.util.Locale
import java.util.concurrent.TimeUnit

class EmailCodeFragment : Fragment() {

    private var _binding: FragmentEmailCodeBinding? = null
    private val  binding get() = _binding!!

    private lateinit var timer: CountDownTimer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEmailCodeBinding.inflate(inflater, container, false)

        val view = binding.root

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