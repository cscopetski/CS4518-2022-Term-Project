package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.DailyFoodItem
import com.example.rnsmfitness.Entities.FoodItem
import com.example.rnsmfitness.Entities.FoodItemBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.sql.Date

private const val URL_PREFIX = "/api/daily-food-log"

interface DailyFoodLogService {

    @GET("$URL_PREFIX/get/{date}")
    fun getDailyFoodLogByDate(@Path("date") date: Date): Call<List<DailyFoodItem>>

    @POST("$URL_PREFIX/update")
    fun updateDailyFoodLogItem(@Body update: DailyFoodBody): Call<ResponseBody>

    @POST("$URL_PREFIX/delete")
    fun deleteDailyFoodLogItem(@Body id:Int): Call<ResponseBody>

    @POST("$URL_PREFIX/add")
    fun insertDailyFoodLogItem(@Body foodId: Int, quantity: Double, date: Date, meal: String): Call<ResponseBody>

}

data class DailyFoodBody(val id: Int,val quantity: Double,val meal: String)