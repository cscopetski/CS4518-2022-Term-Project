package com.example.rnsmfitness.services

import com.example.rnsmfitness.Entities.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.sql.Date

private const val URL_PREFIX = "/api/daily-log"

interface DailyLogService {



    @GET("$URL_PREFIX/get/{date}")
    fun getDailyLog(@Path("date") date: Date): Call<DailyLog>

}

data class DailyLog(val weight_results: Double, val weight_goal: Double, val protein_results: Int, val protein_goal:Int, val fat_results: Int, val carb_results: Int, val carb_goal: Int, val calorie_results: Int, val calorie_goal: Int)