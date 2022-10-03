package com.example.rnsmfitness.Entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

private const val TAG = "DataSource"

class DataSource {

    var id_counter = 1000
    private val liveList: MutableLiveData<List<FoodItem>> = MutableLiveData()

    fun insertFood(name: String?, protein: Int?,carbs: Int?,fat: Int?, serving_size: Double?){
        if (name == null || protein == null || carbs == null || fat == null || serving_size == null) {
            return
        }
        //get the id and user ID to insert into this statement
        val calories = protein * 4 + carbs * 4 + fat * 9
        addFood(FoodItem(id_counter, 100, name, 1, protein, carbs, fat, calories, serving_size))
        id_counter++
        Log.d(TAG, "Food not adding to recycler view")
    }

    private fun addFood(food: FoodItem){

        val list = liveList.value
        if (list == null){
            liveList.postValue(listOf(food))
        } else {
            Log.d(TAG, "add food")
            val updatedList = list.toMutableList()
            updatedList.add(0, food)
            liveList.postValue(updatedList)
        }
    }

    fun deleteFood(food: FoodItem) {
        val currentList = liveList.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(food)
            liveList.postValue(updatedList)
        }

    }

    fun getFoodList(): LiveData<List<FoodItem>> {

        return liveList
    }

    fun setFoodList(list: List<FoodItem>?){
        if (list.isNullOrEmpty()){
            val eList: List<FoodItem> = listOf()
            liveList.postValue(eList)
        }else {
            liveList.postValue(list)
        }

    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}