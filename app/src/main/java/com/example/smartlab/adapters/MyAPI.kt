package com.example.smartlab.adapters


import com.example.smartlab.models.Profile
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Header
import retrofit2.http.POST

interface MyAPI {

    
    @POST("api/SendCode")
    fun postEmail(@Header("User-email") email: String ): Call<String>

    @POST("api/SignIn")
    fun postSignIn(@Header("User-email") email: String, @Header("User-code") code: String) :Call<String>

    @POST("api/CreateProfile")
    suspend fun postCreateProfile(@Header("Authorization")string: String, @Body profile: Profile) : Response<Profile>
}