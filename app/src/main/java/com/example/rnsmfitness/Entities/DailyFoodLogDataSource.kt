package com.example.rnsmfitness.Entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

private const val TAG = "DailyFoodLogDataSource"

class DailyFoodLogDataSource {

    private val liveList: MutableLiveData<List<DailyFoodItem>> = MutableLiveData()


    fun getFoodList(): LiveData<List<DailyFoodItem>> {
        return liveList
    }


    fun getDBFoods(date: Date) {
        val call: Call<List<DailyFoodItem>> =
            RetroFitClient.dailyFoodLogService.getDailyFoodLogByDate(date)

        call.enqueue(object : Callback<List<DailyFoodItem>> {

            override fun onResponse(call: Call<List<DailyFoodItem>>, response: Response<List<DailyFoodItem>>) {

                if (response.isSuccessful) {
                    Log.v(TAG, response.body().toString())
                    val foodList: List<DailyFoodItem> = response.body()!!
                    setFoodList(foodList)
                } else {
                    setFoodList(null)
                    Log.d(TAG, response.code().toString())

                }
            }

            override fun onFailure(call: Call<List<DailyFoodItem>>, t: Throwable) {

                setFoodList(null)
                Log.d(TAG, t.message!!)

            }

        })
    }

    fun setFoodList(list: List<DailyFoodItem>?){
        if (list.isNullOrEmpty()){
            val eList: List<DailyFoodItem> = listOf()
            liveList.postValue(eList)
        }else {
            liveList.postValue(list)
        }

    }

    fun getMealCalories(meal: String): Int{
        var calTotal = 0;
        for (item in liveList.value!!){
            if (item.meal == meal){
                calTotal += (item.calories * item.quantity).toInt()
            }
        }
        return calTotal
    }

    companion object {
        private var INSTANCE: DailyFoodLogDataSource? = null

        fun getDataSource(): DailyFoodLogDataSource {
            return synchronized(DailyFoodLogDataSource::class) {
                val newInstance = INSTANCE ?: DailyFoodLogDataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }

}