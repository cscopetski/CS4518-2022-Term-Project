package com.example.rnsmfitness.Entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.RetroFitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

private const val TAG = "USDADataSource"

class USDADataSource {

    private val liveList: MutableLiveData<List<USDAFoodItem>> = MutableLiveData()

    fun getFoodList(): LiveData<List<USDAFoodItem>> {

        return liveList
    }

    fun setFoodList(list: List<USDAFoodItem>?){
        if (list.isNullOrEmpty()){
            val eList: List<USDAFoodItem> = listOf()
            liveList.postValue(eList)
        }else {
            liveList.postValue(list)
        }

    }

    companion object {
        private var INSTANCE: USDADataSource? = null

        fun getDataSource(): USDADataSource {
            return synchronized(USDADataSource::class) {
                val newInstance = INSTANCE ?: USDADataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }


}