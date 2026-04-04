package com.satyaai.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.satyaai.app.data.model.ChatMessageEntity

@Database(entities = [ChatMessageEntity::class], version = 1, exportSchema = false)
abstract class SatyaDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}
