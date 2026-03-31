package com.satyaai.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satyaai.app.data.model.ChatMessageEntity
import com.satyaai.app.data.repository.AssistantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AssistantRepository
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val openAiKey = MutableStateFlow("")

    val messages = repository.messages().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val uiState: StateFlow<ChatUiState> = combine(messages, isLoading, openAiKey) { msgs, loading, key ->
        ChatUiState(msgs, loading, key)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ChatUiState())

    fun updateKey(key: String) {
        openAiKey.value = key
    }

    fun send(text: String) {
        if (text.isBlank() || openAiKey.value.isBlank()) return
        viewModelScope.launch {
            isLoading.value = true
            runCatching {
                repository.ask(openAiKey.value, messages.value, text)
            }
            isLoading.value = false
        }
    }
}

data class ChatUiState(
    val messages: List<ChatMessageEntity> = emptyList(),
    val isLoading: Boolean = false,
    val openAiKey: String = ""
)
