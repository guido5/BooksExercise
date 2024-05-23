package com.guido.booksexercise.data.roomRepository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface VolumesDao {

    @Transaction
    @Query("SELECT * FROM VolumesEntity")
    fun getVolumesWithAuthor(): List<VolumesWithAuthor>

    @Insert
    fun insert(vararg volumesWithAuthor: VolumesEntity)
}