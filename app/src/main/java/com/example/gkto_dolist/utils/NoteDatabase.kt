package com.example.gkto_dolist.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NotesEntity::class], exportSchema = false , version = 1)
abstract class NoteDatabase() : RoomDatabase() {
    abstract val notesDao : NoteDao
    companion object{
        @Volatile
          var instance : NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase{
            if (instance == null) {
                synchronized(this){
                    instance = Room.databaseBuilder(context,
                        NoteDatabase::class.java,
                        "NoteDB")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }


    }
}