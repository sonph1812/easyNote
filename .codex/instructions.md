# Agent Instructions: Easy Note App Development & Maintenance

This document guides an AI Agent on how to understand, rebuild, or maintain the **Easy Note** project.

## 1. Project Context
- **App Name:** Easy Note
- **Goal:** A simple, modern, high-performance note-taking application.
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Architecture:** MVVM (Model-View-ViewModel) combined with basic Clean Architecture.

## 2. Tech Stack & Dependencies
The Agent should pay attention to the core components:
- **Database:** Room Persistence Library (for offline storage).
- **Dependency Injection:** (Check the project to see if it uses Hilt, Koin, or manual providers).
- **Backend/Services:** Firebase (Analytics, Google Services).
- **Build System:** Gradle Kotlin DSL (`.gradle.kts`).

## 3. Critical Configurations
When working with this project, the Agent MUST comply with:
- **Application ID:** Must always match the `google-services.json` file. Currently `com.example.take_note_app_1`.
- **Git Ignore:** Do not push personal documentation `.md` files, except for `README.md`.
- **Branding:** The app uses a **Gold & White** color scheme for the icon and a premium UI direction.

## 4. Folder Structure
- `data/`: Contains Entities (Note), DAO, Database, and Repository.
- `ui/`: Contains Composable functions, ViewModel, and Theme (Color.kt, Type.kt).
- `res/`: Resource management, especially `drawable/ic_launcher_*` for Adaptive Icons.

## 5. Guide for Developing New Features
When the Agent is asked to add a feature:
1. **Model:** Update `Note.kt` if new data fields are needed.
2. **Data Layer:** Add functions to `NoteDao.kt` and `NoteRepository.kt`.
3. **Logic:** Handle logic in the corresponding ViewModel.
4. **UI:** Build screens using Jetpack Compose, ensuring compliance with the Gold/White Theme.

## 6. Pre-Commit/Push Checklist
- [ ] Check `applicationId` in `build.gradle.kts`.
- [ ] Run Gradle Sync to ensure no dependency errors.
- [ ] Check icon display on various screen types (Adaptive Icon).
- [ ] Ensure no sensitive information (API Keys) is exposed.

---
*This document helps the Agent quickly take over the project and maintain consistency.*
