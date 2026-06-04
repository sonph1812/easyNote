package com.example.take_note_app_1

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.take_note_app_1.data.Note
import com.example.take_note_app_1.data.NoteDao
import com.example.take_note_app_1.data.NoteDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsNoteIntoDB() = runBlocking {
        val note = Note(id = 1, title = "Title", content = "Content")
        noteDao.insertNote(note)
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], note)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllNotes_returnsAllNotesFromDB() = runBlocking {
        val note1 = Note(id = 1, title = "Title 1", content = "Content 1")
        val note2 = Note(id = 2, title = "Title 2", content = "Content 2")
        noteDao.insertNote(note1)
        noteDao.insertNote(note2)
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes[0], note2) // note2 has later timestamp by default
        assertEquals(allNotes[1], note1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetNote_returnsNoteFromDB() = runBlocking {
        val note = Note(id = 1, title = "Title", content = "Content")
        noteDao.insertNote(note)
        val fetchedNote = noteDao.getNoteById(1)
        assertEquals(fetchedNote, note)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateNotes_updatesNotesInDB() = runBlocking {
        val note = Note(id = 1, title = "Title", content = "Content")
        noteDao.insertNote(note)
        val updatedNote = note.copy(title = "Updated Title")
        noteDao.updateNote(updatedNote)
        val fetchedNote = noteDao.getNoteById(1)
        assertEquals(fetchedNote, updatedNote)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteNotes_deletesNotesFromDB() = runBlocking {
        val note = Note(id = 1, title = "Title", content = "Content")
        noteDao.insertNote(note)
        noteDao.deleteNote(note)
        val allNotes = noteDao.getAllNotes().first()
        assertEquals(allNotes.size, 0)
    }
}
