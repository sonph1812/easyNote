package com.example.take_note_app_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.take_note_app_1.data.Note
import com.example.take_note_app_1.ui.theme.Takenoteapp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Takenoteapp1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val viewModel: NoteViewModel = viewModel()
                    var currentScreen by remember { mutableStateOf<Screen>(Screen.List) }
                    var noteToEdit by remember { mutableStateOf<Note?>(null) }

                    when (currentScreen) {
                        is Screen.List -> {
                            NoteListScreenWrapper(
                                viewModel = viewModel,
                                onNoteClick = { note ->
                                    noteToEdit = note
                                    currentScreen = Screen.AddEdit
                                },
                                onAddNoteClick = {
                                    noteToEdit = null
                                    currentScreen = Screen.AddEdit
                                }
                            )
                        }
                        is Screen.AddEdit -> {
                            AddEditNoteScreen(
                                note = noteToEdit,
                                onSave = { title, content ->
                                    if (noteToEdit == null) {
                                        viewModel.addNote(title, content)
                                    } else {
                                        viewModel.updateNote(noteToEdit!!.copy(title = title, content = content))
                                    }
                                    currentScreen = Screen.List
                                },
                                onBack = {
                                    currentScreen = Screen.List
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen {
    object List : Screen()
    object AddEdit : Screen()
}
