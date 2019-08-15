package com.recyclerview.sample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.recyclerview.sample.model.Profile

@Database(entities = [Profile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}