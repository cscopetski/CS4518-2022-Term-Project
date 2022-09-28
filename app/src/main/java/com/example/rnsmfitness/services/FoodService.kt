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

    @POST("$URL_PREFIX/edit")
    fun deleteFood(@Body id: Int, @Body is_visible: Int): Call<ResponseBody>
    //I added a second @body because it said I needed an annotation for second arg of this method, and then i got error for have two @Body
    //this function is here and then called from My food list line 165, which is where I'm defining the local version of delete food
    //local version of delete food called in onSwiped MyFoodList line 101
    //using logs, know it get into the else statement

}