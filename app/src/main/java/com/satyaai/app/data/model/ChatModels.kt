package com.satyaai.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val role: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class ChatRequest(
    val model: String = "gpt-4.1-mini",
    val messages: List<ChatMessagePayload>,
    val stream: Boolean = true
)

data class ChatMessagePayload(
    val role: String,
    val content: String
)

data class ChatChoice(
    val message: ChatMessagePayload
)

data class ChatResponse(
    val id: String,
    val choices: List<ChatChoice>
)
