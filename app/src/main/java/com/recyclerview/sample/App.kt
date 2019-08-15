package com.recyclerview.sample

import android.app.Application
import androidx.room.Room
import com.recyclerview.sample.db.AppDatabase

class App : Application() {

    companion object {
        lateinit var APP_DATABASE: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        APP_DATABASE = Room.databaseBuilder(this, AppDatabase::class.java, "sample-db").allowMainThreadQueries().build()
    }
}