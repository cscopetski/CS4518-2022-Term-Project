package com.example.rnsmfitness

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: BaseApplication? = null

        fun getAppContext(): Context {
            return instance!!.applicationContext
        }
    }
}
