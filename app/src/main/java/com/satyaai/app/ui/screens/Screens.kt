package com.satyaai.app.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.satyaai.app.data.model.FileAttachment
import com.satyaai.app.ui.navigation.NavRoutes
import com.satyaai.app.util.FileTextExtractor
import com.satyaai.app.viewmodel.MainViewModel

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) { onDone() }
    ScreenContainer(title = "Satya AI") { Text("Initializing futuristic assistant...") }
}

@Composable
fun GoogleSignInScreen(onSignedIn: () -> Unit) {
    ScreenContainer(title = "Google Sign-In") {
        Text("Connect Google Workspace (Gmail, Drive, Calendar, Photos) via OAuth 2.0")
        Button(onClick = onSignedIn) { Text("Continue") }
    }
}

@Composable
fun HomeChatScreen(vm: MainViewModel, navController: NavController) {
    val state by vm.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    ScreenContainer(title = "Satya AI Chat") {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { navController.navigate(NavRoutes.Voice) }) { Text("Voice") }
            Button(onClick = { navController.navigate(NavRoutes.Files) }) { Text("Files") }
            Button(onClick = { navController.navigate(NavRoutes.Automation) }) { Text("Automation") }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.messages) { msg ->
                Text("${msg.role.uppercase()}: ${msg.content}", style = MaterialTheme.typography.bodyMedium)
            }
        }
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message Satya AI") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.send(message); message = "" }) { Text("Send") }
            if (state.isLoading) CircularProgressIndicator()
        }
    }
}

@Composable
fun VoiceAssistantScreen(vm: MainViewModel) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { }
    var transcript by remember { mutableStateOf("") }

    ScreenContainer(title = "Voice Assistant") {
        Text("Wake word: Hey Satya")
        Button(onClick = { permissionLauncher.launch(Manifest.permission.RECORD_AUDIO) }) {
            Text("Grant Microphone")
        }
        OutlinedTextField(value = transcript, onValueChange = { transcript = it }, label = { Text("Voice transcript") })
        Button(onClick = { vm.send(transcript) }) { Text("Send voice command") }
        Text("Animated voice wave placeholder")
    }
}

@Composable
fun FileUploadScreen(vm: MainViewModel) {
    val context = LocalContext.current
    var attachment by remember { mutableStateOf<FileAttachment?>(null) }

    val picker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val mime = context.contentResolver.getType(it)
            val name = queryName(context, it)
            val text = FileTextExtractor.extract(context, it, mime)
            attachment = FileAttachment(it, name, mime.orEmpty(), text)
        }
    }

    ScreenContainer(title = "File Upload & Analysis") {
        Button(onClick = { picker.launch("*/*") }) { Text("Attach file from storage / Drive") }
        attachment?.let {
            Text("File: ${it.name} (${it.mimeType})")
            Text("Extract preview: ${it.extractedText.take(240)}")
            Button(onClick = { vm.send("Analyze this file: ${it.name}\n${it.extractedText}") }) {
                Text("Analyze with ChatGPT")
            }
        }
    }
}

@Composable fun IntegrationsScreen() = ScreenContainer("Integrations") { Text("Gmail, Drive, Calendar, Photos integration hooks") }
@Composable fun NotesRemindersScreen() = ScreenContainer("Notes & Reminders") { Text("Notes, todos, reminders UI") }
@Composable fun AutomationScreen() = ScreenContainer("Automation") { Text("Calls, SMS, WiFi/Bluetooth, alarms, notifications") }

@Composable
fun SettingsScreen(vm: MainViewModel) {
    var key by remember { mutableStateOf("") }
    ScreenContainer("Settings") {
        OutlinedTextField(value = key, onValueChange = { key = it }, label = { Text("OpenAI API Key") })
        Button(onClick = { vm.updateKey(key) }) { Text("Save Key") }
    }
}

@Composable
private fun ScreenContainer(title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        content()
    }
}

private fun queryName(context: Context, uri: Uri): String {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst() && index >= 0) return it.getString(index)
    }
    return uri.toString().substringAfterLast('/').ifBlank { "attachment" }
}
