package com.example.rnsmfitness.Entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

private const val TAG = "DailyLogDataSource"

class DailyFoodLogDataSource {

    private val liveList: MutableLiveData<List<DailyFoodItem>> = MutableLiveData()

//    fun insertFood(name: String?, protein: Int?,carbs: Int?,fat: Int?, serving_size: Double?){
//        if (name == null || protein == null || carbs == null || fat == null || serving_size == null) {
//            return
//        }
//        //get the id and user ID to insert into this statement
//        val calories = protein * 4 + carbs * 4 + fat * 9
//        addFood(DailyFoodItem(0, name, 0,protein, carbs, fat, calories, serving_size, 0.0, "breakfast"))
//        Log.d(TAG, "Food not adding to recycler view")
//    }
//
//    private fun addFood(food: DailyFoodItem){
//
//        val list = liveList.value
//        if (list == null){
//            liveList.postValue(listOf(food))
//        } else {
//            Log.d(TAG, "add food")
//            val updatedList = list.toMutableList()
//            updatedList.add(0, food)
//            liveList.postValue(updatedList)
//        }
//    }
//
//    fun deleteFood(food: DailyFoodItem) {
//        val currentList = liveList.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(food)
//            liveList.postValue(updatedList)
//        }
//
//    }

    fun getFoodList(): LiveData<List<DailyFoodItem>> {
        return liveList
    }

//    fun getMealList(meal:String):List<DailyFoodItem> {
//
//        val currentList = liveList.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.filter { it.meal === meal }
//            return updatedList;
//        }
//        return listOf()
//    }

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