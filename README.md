# Satya AI (Android)

Satya AI is a Jarvis-like Android assistant built with **Kotlin + Jetpack Compose + MVVM** and powered by the **OpenAI ChatGPT API**.

## Implemented foundation

- Voice command flow scaffolding (wake-word UX, `SpeechRecognizer` permissions, transcript to chat).
- ChatGPT text/voice conversation scaffolding with Room-backed history.
- File attach + extraction dispatcher for PDF, DOCX, XLSX, PPTX, TXT, images, audio, video, ZIP.
- Google Workspace integration entry points (OAuth screen + integration module placeholders).
- Mobile automation module scaffolding (alarms/settings hooks + automation screen).
- App navigation with requested screens:
  - Splash
  - Google Sign In
  - Home Chat
  - Voice Assistant
  - File Upload
  - Integrations
  - Notes & Reminders
  - Automation
  - Settings

## Tech Stack

- Kotlin
- Jetpack Compose
- MVVM
- Retrofit + Moshi
- Room
- WorkManager
- Hilt DI
- Android `SpeechRecognizer` + `TextToSpeech`
- Google Sign-In SDK base dependency

## Setup

1. Open in Android Studio (JDK 17).
2. Sync Gradle.
3. Run app on Android 8+ device/emulator.
4. Add your API key in **Settings screen** at runtime.
5. For production, move API key handling to a secure backend + token exchange.

## Important production TODOs

- Move OpenAI calls to backend proxy (never ship raw key in client).
- Implement true streaming responses (SSE/WebSocket).
- Add real file parsers:
  - PDFBox / iText for PDF
  - Apache POI for Office docs
  - OCR for images
  - Whisper transcription for audio/video
- Complete Google OAuth token flow + Gmail/Drive/Calendar/Photos service wrappers.
- Implement automation actions requiring runtime permissions and OEM-specific handling.
- Add tests, error handling, encryption, and offline caching.
