package com.example.rnsmfitness.Entities

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rnsmfitness.RetroFitClient
import com.example.rnsmfitness.services.DailyLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date

private const val TAG = "DailyLogDataSource"

class DailyLogDataSource {

    private val dailyLog: MutableLiveData<DailyLog> = MutableLiveData()


    fun getDailyLog(): LiveData<DailyLog> {
        return dailyLog
    }

    fun getDailyDetails(date: Date) {
        val call: Call<DailyLog> =
            RetroFitClient.dailyLogService.getDailyLog(date)

        call.enqueue(object : Callback<DailyLog> {

            override fun onResponse(call: Call<DailyLog>, response: Response<DailyLog>) {

                if (response.isSuccessful) {
                    Log.v(TAG, response.body().toString())
                    val dLog: DailyLog = response.body()!!
                    setLog(dLog)
                } else {
                    setLog(null)
                    Log.d(TAG, response.code().toString())

                }
            }

            override fun onFailure(call: Call<DailyLog>, t: Throwable) {
                setLog(null)
                Log.d(TAG, t.message!!)
            }

        })
    }



    fun setLog(log: DailyLog?){
        if (log == null){
            val eLog = DailyLog(0.0,0.0,0,0,0,0,0,0,0,0)
            dailyLog.postValue(eLog)
        }else {
            dailyLog.postValue(log)
        }

    }

    companion object {
        private var INSTANCE: DailyLogDataSource? = null

        fun getDataSource(): DailyLogDataSource {
            return synchronized(DailyLogDataSource::class) {
                val newInstance = INSTANCE ?: DailyLogDataSource()
                INSTANCE = newInstance
                newInstance
            }
        }
    }

}