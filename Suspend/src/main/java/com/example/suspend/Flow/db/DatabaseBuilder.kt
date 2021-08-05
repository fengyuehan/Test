package com.example.suspend.Flow.db

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE:AppDatabase? = null

    fun getInstance(context: Context):AppDatabase{
        if (INSTANCE == null){
            synchronized(AppDatabase::class){
                if (INSTANCE== null){
                    INSTANCE = buildRoomDB(context)
                }
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context): AppDatabase? {
        return Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"flow-example-coroutines").build()

    }
}