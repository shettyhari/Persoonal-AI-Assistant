package com.satyaai.app.automation

sealed class AutomationIntent {
    data class SetAlarm(val hour: Int, val minute: Int, val label: String) : AutomationIntent()
    data object OpenSettings : AutomationIntent()
    data class Unsupported(val reason: String) : AutomationIntent()
}

object AutomationParser {
    fun parse(command: String): AutomationIntent {
        val lowercase = command.lowercase()
        return when {
            "alarm" in lowercase -> AutomationIntent.SetAlarm(7, 0, "Satya AI alarm")
            "settings" in lowercase -> AutomationIntent.OpenSettings
            else -> AutomationIntent.Unsupported("No local automation mapping found")
        }
    }
}
