package com.thanglong.easynote

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.thanglong.easynote.data.Note
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteListScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit,
    onDeleteClick: (Note) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    val filteredNotes = remember(notes, searchQuery) {
        if (searchQuery.isBlank()) {
            notes.sortedByDescending { it.timestamp }
        } else {
            notes.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.content.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
            ) {
                if (isSearchActive) {
                    SearchAppBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onCloseClick = {
                            isSearchActive = false
                            searchQuery = ""
                        }
                    )
                } else {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                "Easy Note",
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        actions = {
                            IconButton(onClick = { isSearchActive = true }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = onAddNoteClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(20.dp)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Note",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (filteredNotes.isEmpty()) {
                EmptyNotesState(
                    isSearch = searchQuery.isNotBlank(),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalItemSpacing = 12.dp
                ) {
                    items(filteredNotes, key = { it.id }) { note ->
                        NoteItem(
                            note = note,
                            onClick = { onNoteClick(note) },
                            onDeleteClick = { noteToDelete = note }
                        )
                    }
                }
            }
        }
    }

    // Delete Confirmation Dialog
    noteToDelete?.let { note ->
        AlertDialog(
            onDismissRequest = { noteToDelete = null },
            title = { Text("Delete Note") },
            text = { Text("Are you sure you want to delete this note?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(note)
                        noteToDelete = null
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { noteToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit
) {
    TopAppBar(
        title = {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = { Text("Search your notes...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = onCloseClick) {
                Icon(Icons.Default.Close, contentDescription = "Close Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun EmptyNotesState(
    isSearch: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(120.dp),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = if (isSearch) "🔍" else "📝",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = if (isSearch) "No results found" else "Stay Organized",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isSearch) "We couldn't find what you're looking for." else "Start your journey by creating your first note today!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateString = remember(note.timestamp) {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
        sdf.format(Date(note.timestamp))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 10,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = dateString,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun NoteListScreenWrapper(
    viewModel: NoteViewModel,
    onNoteClick: (Note) -> Unit,
    onAddNoteClick: () -> Unit
) {
    val notes by viewModel.allNotes.collectAsState()
    NoteListScreen(
        notes = notes,
        onNoteClick = onNoteClick,
        onAddNoteClick = onAddNoteClick,
        onDeleteClick = { viewModel.deleteNote(it) }
    )
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    val sampleNotes = listOf(
        Note(id = 1, title = "Learn Android", content = "Master Jetpack Compose and Room Database"),
        Note(id = 2, title = "Grocery Shopping", content = "Buy vegetables, meat, and eggs"),
        Note(id = 3, title = "Important Note", content = "Don't forget the project deadline")
    )
    MaterialTheme {
        NoteListScreen(
            notes = sampleNotes,
            onNoteClick = {},
            onAddNoteClick = {},
            onDeleteClick = {}
        )
    }
}
