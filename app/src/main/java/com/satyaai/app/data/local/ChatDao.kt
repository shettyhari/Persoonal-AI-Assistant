package com.satyaai.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.satyaai.app.data.model.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    @Insert
    suspend fun insertMessage(message: ChatMessageEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun observeMessages(): Flow<List<ChatMessageEntity>>
}
