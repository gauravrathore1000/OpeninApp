package com.example.openinapp.base

import android.app.Application
import android.content.Context

class BaseApplication : Application() {

    companion object {
        private lateinit var context: Context
        private lateinit var instance: BaseApplication

        public fun getInstance(): BaseApplication {
            return instance
        }

        fun getContext(): Context {
            return context
        }
    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
    }
}