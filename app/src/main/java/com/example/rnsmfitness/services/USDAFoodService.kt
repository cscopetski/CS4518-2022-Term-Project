package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import com.example.rnsmfitness.Entities.USDAFoodItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

private const val URL_PREFIX = "/api/usda"


interface USDAFoodService {

    @GET("$URL_PREFIX/get-food/{upcId}")
    fun getFoodByUPCId(@Path("upcId") upcId: String): Call<USDAFoodItem>

    @GET("$URL_PREFIX/search-foods/{query}")
    fun searchFood(@Path("query") query: String): Call<List<USDAFoodItem>>

}