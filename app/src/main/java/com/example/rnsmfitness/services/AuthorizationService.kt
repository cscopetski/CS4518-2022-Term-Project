package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AuthorizationService {

    @POST("auth/login")
    fun login(@Body loginCredentials: LoginCredentials): Call<User>

    @POST("auth/logout")
    fun logout(): Call<ResponseBody>

}