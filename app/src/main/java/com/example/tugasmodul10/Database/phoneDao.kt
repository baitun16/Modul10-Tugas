package com.example.tugasmodul10.Database

import androidx.lifecycle.LiveData
import androidx.room.*

//pengelolaan query database
@Dao
interface phoneDao {

    @Insert
    fun insert(phone: phone)

    @Update
    fun update(phone: phone)

    @Delete
    fun delete(phone: phone)

    @Query("DELETE FROM phone_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM phone_table ORDER BY Nama DESC")
    fun getAllNotes(): LiveData<List<phone>>

}