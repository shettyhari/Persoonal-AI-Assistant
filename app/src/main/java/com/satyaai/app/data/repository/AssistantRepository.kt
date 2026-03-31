package com.satyaai.app.data.repository

import com.satyaai.app.data.local.ChatDao
import com.satyaai.app.data.model.ChatMessageEntity
import com.satyaai.app.data.model.ChatMessagePayload
import com.satyaai.app.data.model.ChatRequest
import com.satyaai.app.data.remote.OpenAiApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssistantRepository @Inject constructor(
    private val api: OpenAiApi,
    private val dao: ChatDao
) {
    fun messages(): Flow<List<ChatMessageEntity>> = dao.observeMessages()

    suspend fun ask(openAiApiKey: String, history: List<ChatMessageEntity>, userInput: String): String {
        dao.insertMessage(ChatMessageEntity(role = "user", content = userInput))
        val prompt = listOf(
            ChatMessagePayload(
                role = "system",
                content = "You are Satya AI, a proactive personal assistant for productivity, files, and automation."
            )
        ) + history.takeLast(12).map { ChatMessagePayload(it.role, it.content) } +
            ChatMessagePayload(role = "user", content = userInput)

        val response = api.chatCompletion(
            authHeader = "Bearer $openAiApiKey",
            request = ChatRequest(messages = prompt)
        )
        val assistantText = response.choices.firstOrNull()?.message?.content.orEmpty()
        dao.insertMessage(ChatMessageEntity(role = "assistant", content = assistantText))
        return assistantText
    }
}
