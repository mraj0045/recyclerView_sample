package com.recyclerview.sample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.recyclerview.sample.model.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * from Profile")
    fun getAll(): LiveData<List<Profile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Delete
    fun delete(profile: Profile)
}