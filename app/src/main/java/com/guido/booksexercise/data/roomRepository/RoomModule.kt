package com.guido.booksexercise.data.roomRepository

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.example.baseproject.model.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object RoomModule {

    @Provides
    fun provideRoomDB(@ApplicationContext context: Context): AppDatabase {
        return databaseBuilder(context, AppDatabase::class.java, "main-database").build()
    }

    @Provides
    fun provideVolumesDao(appDatabase: AppDatabase): VolumesDao {
        return appDatabase.volumesDao()
    }
}