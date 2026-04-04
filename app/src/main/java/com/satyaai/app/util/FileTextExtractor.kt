package com.satyaai.app.util

import android.content.Context
import android.net.Uri

object FileTextExtractor {
    fun extract(context: Context, uri: Uri, mimeType: String?): String {
        return when {
            mimeType?.contains("text") == true -> {
                context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() }.orEmpty()
            }
            mimeType == "application/pdf" -> "PDF parsing placeholder. Integrate PdfBox / ML Kit Document Scanner."
            mimeType?.contains("word") == true -> "DOCX parsing placeholder. Integrate Apache POI."
            mimeType?.contains("sheet") == true -> "XLSX parsing placeholder. Integrate Apache POI."
            mimeType?.contains("presentation") == true -> "PPTX parsing placeholder. Integrate Apache POI."
            mimeType?.startsWith("image/") == true -> "Image detected. Route to vision model analysis."
            mimeType?.startsWith("audio/") == true -> "Audio detected. Route to transcription endpoint."
            mimeType?.startsWith("video/") == true -> "Video detected. Extract audio + summarize."
            mimeType == "application/zip" -> "ZIP detected. Unzip securely, index contained files."
            else -> "Unsupported or unknown file type."
        }
    }
}
