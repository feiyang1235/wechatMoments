package com.person.ermao.thoughtworkshomework

import android.app.Application
import android.content.Context

public class App:Application() {
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        context = this
    }
    companion object{
        private lateinit var context:Context
        @JvmStatic
        fun getApp()= context
    }
}