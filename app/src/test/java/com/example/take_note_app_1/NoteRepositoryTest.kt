package com.example.take_note_app_1

import com.example.take_note_app_1.data.Note
import com.example.take_note_app_1.data.NoteDao
import com.example.take_note_app_1.data.NoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteRepositoryTest {

    private val noteDao = mockk<NoteDao> {
        every { getAllNotes() } returns flowOf(emptyList())
    }
    private val repository = NoteRepository(noteDao)

    @Test
    fun `insertNote calls dao insertNote`() = runTest {
        val note = Note(id = 1, title = "Test", content = "Content")
        coEvery { noteDao.insertNote(note) } returns Unit

        repository.insertNote(note)

        coVerify { noteDao.insertNote(note) }
    }

    @Test
    fun `getNoteById returns note from dao`() = runTest {
        val note = Note(id = 1, title = "Test", content = "Content")
        coEvery { noteDao.getNoteById(1) } returns note

        val result = repository.getNoteById(1)

        assertEquals(note, result)
        coVerify { noteDao.getNoteById(1) }
    }
}
