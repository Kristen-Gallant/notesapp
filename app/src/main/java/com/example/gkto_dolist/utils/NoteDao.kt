package com.example.gkto_dolist.utils

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.*
@Dao
interface NoteDao {
    //Insert note
    @Insert
    suspend fun insertNote(notesEntity: NotesEntity)

    //Get all Notes
    @Query("Select * From `Notes Table` ORDER BY date_and_time DESC")
    fun getAllNotes() : LiveData<List<NotesEntity>>

    //Check if note exists
    @Query("SELECT count() FROM `notes table` WHERE id =:key")
    suspend fun checkNote(key : Int) : Int

    //Update note if exists
    @Query("UPDATE `notes table` SET date_and_time =:new_date , subject = :subject , note = :note  WHERE id =:noteid")
    suspend fun updateNote(subject: String, note: String, noteid: String, new_date: String)






}