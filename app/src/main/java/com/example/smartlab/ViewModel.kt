package com.example.smartlab

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartlab.models.Profile
import com.example.smartlab.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ViewModel(private val repository: Repository):ViewModel() {

    var myResponseProfile: MutableLiveData<Response<Profile>> = MutableLiveData()

    fun postCreateProfile(header: String, body: Profile){
        viewModelScope.launch {
            val response = repository.postCreateProfile(header, body)
            myResponseProfile.value = response
        }
    }


}