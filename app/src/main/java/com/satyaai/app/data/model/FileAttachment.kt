package com.satyaai.app.data.model

import android.net.Uri


data class FileAttachment(
    val uri: Uri,
    val name: String,
    val mimeType: String,
    val extractedText: String = ""
)
