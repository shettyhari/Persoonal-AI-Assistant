# Satya AI Android Application

Satya AI is a mobile Jarvis-like assistant that combines:
- OpenAI ChatGPT intelligence
- Voice commands (`SpeechRecognizer` + `TextToSpeech`)
- File upload + file-aware prompting
- Google Workspace integration hooks
- Phone automation foundations

## Included screens
- Splash
- Google Sign In
- Home Chat
- Voice Assistant
- File Upload
- Integrations
- Notes & Reminders
- Automation
- Settings

## Current implementation status

### Working foundation
- Compose UI + Navigation + dark futuristic style
- MVVM architecture with Hilt dependency injection
- Room database for chat history
- Retrofit OpenAI chat completion call path
- Voice manager with listen + speak controls
- File picker and MIME-aware extraction routing
- Daily summary worker scaffold

### TODO for production
- Backend-proxied OpenAI key exchange (do not keep API keys on device)
- True streaming response rendering
- Real parsers for PDF, DOCX, XLSX, PPTX, OCR, audio transcription
- Google OAuth token exchange + Gmail/Drive/Calendar/Photos API clients
- Runtime permission UX and automation safety controls

## Run locally
1. `./gradlew assembleDebug`
2. Install generated APK on Android 8.0+ device/emulator.
3. Open **Settings** and set OpenAI key for development tests.
