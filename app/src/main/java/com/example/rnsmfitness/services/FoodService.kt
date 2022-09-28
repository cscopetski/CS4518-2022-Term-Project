package com.example.rnsmfitness.services

import android.telecom.CallScreeningService
import android.util.Log
import com.example.rnsmfitness.Activities.TAG
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.Entities.LoginCredentials
import com.example.rnsmfitness.Entities.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

private const val URL_PREFIX = "/api/food"


interface FoodService {
    @GET("$URL_PREFIX/getAll-user")
    fun getAllUserFoods(): Call<List<FoodItem>>

    //can be used for specific details when making individual food pages
    @GET("$URL_PREFIX/get/{id}")
    fun getFoodByID(@Path ("id") foodId: Int): Call<FoodItem>

    @POST("$URL_PREFIX/insert")
    fun insertFood(@Body food: FoodItemBody): Call<ResponseBody>

    @POST("$URL_PREFIX/update")
    fun updateFood(@Body food: FoodItemBody): Call<ResponseBody>

    @POST("$URL_PREFIX/update")
    fun deleteFood(@Body food: FoodItem): Call<ResponseBody>


}