package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

private const val URL_PREFIX = "/api/food"


interface FoodService {
    @GET("$URL_PREFIX/getAll-user")
    fun getAllUserFoods(): Call<List<FoodItem>>

    @POST("$URL_PREFIX/insert")
    fun insertFood(@Body food: FoodItemBody): Call<ResponseBody>

    @POST("$URL_PREFIX/update")
    fun updateFood(@Body food: FoodItem): Call<ResponseBody>

    @POST("$URL_PREFIX/update")
    fun deleteFood(@Body food: FoodItem): Call<ResponseBody>


}