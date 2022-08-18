package com.example.gkto_dolist.utils

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Notes Table")
data class NotesEntity(@PrimaryKey(autoGenerate = true)val id : Int,val secondaryId : String ,  val subject : String,
                       val note : String , val date_and_time : String) : Parcelable
