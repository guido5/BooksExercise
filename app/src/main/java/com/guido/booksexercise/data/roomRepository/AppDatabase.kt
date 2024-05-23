package com.example.baseproject.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.guido.booksexercise.data.roomRepository.AuthorsEntity
import com.guido.booksexercise.data.roomRepository.VolumesAuthorsCrossRef
import com.guido.booksexercise.data.roomRepository.VolumesDao
import com.guido.booksexercise.data.roomRepository.VolumesEntity

@Database(entities = [VolumesEntity::class, AuthorsEntity::class, VolumesAuthorsCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun volumesDao() : VolumesDao
}