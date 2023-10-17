package com.example.smartlab.repository

import com.example.smartlab.api.RetrofitInstance
import com.example.smartlab.models.Profile
import retrofit2.Response

class Repository {

    suspend fun postCreateProfile(authorization: String, profile: Profile): Response<Profile>  {
        return RetrofitInstance.myApi.postCreateProfile(authorization, profile)
    }


}