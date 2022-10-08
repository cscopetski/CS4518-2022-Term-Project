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
    fun deleteDailyFoodLogItem(@Body id: DailyFoodId): Call<ResponseBody>

    @POST("$URL_PREFIX/add")
    fun insertDailyFoodLogItem(@Body myFood: MyFoodDailyFood): Call<ResponseBody>

    @POST("$URL_PREFIX/add-usda")
    fun insertUSDADailyFoodLogItem(@Body dailyFood: USDADailyFood): Call<ResponseBody>

}

data class USDADailyFood(val date:Date, val quantity: Double, val meal: String, val food: FoodItemBody)
data class DailyFoodId(val id: Int)
data class DailyFoodBody(val id: Int,val quantity: Double,val meal: String)
data class MyFoodDailyFood(val date:Date, val food_id: Int,val quantity: Double,val meal: String)