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

    private val liveList: MutableLiveData<List<FoodItem>> = MutableLiveData()

    fun getFoodList(): LiveData<List<FoodItem>> {

        return liveList
    }

    fun deleteFood(food: FoodItem) {
        val currentList = liveList.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(food)
            liveList.postValue(updatedList)
        }

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