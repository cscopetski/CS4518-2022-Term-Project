package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

private const val URL_PREFIX = "/api/users"

interface UserService {



    @GET("$URL_PREFIX/get")
    fun getUser(): Call<User>

}