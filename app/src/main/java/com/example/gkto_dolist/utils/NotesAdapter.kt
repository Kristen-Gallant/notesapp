package com.example.gkto_dolist.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gkto_dolist.R
import com.example.gkto_dolist.databinding.NoteslayoutBinding
import com.example.gkto_dolist.notes.NotesDirections

class NotesAdapter(val context : Context) : ListAdapter<NotesEntity, NotesAdapter.ViewHolder>(NotesDiffUtil()) {

    class ViewHolder(binding : NoteslayoutBinding): RecyclerView.ViewHolder(binding.root){
        val subject = binding.subject
        val notep = binding.notepreview
        val lastupdated = binding.lastUpdated
        val edithNote = binding.editNote
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedLayout = NoteslayoutBinding.inflate(layoutInflater)
        return ViewHolder(inflatedLayout)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val data = getItem(position)
        holder.notep.text = data.note
        holder.subject.text = data.subject
        holder.lastupdated.text = data.date_and_time
        holder.edithNote.setOnClickListener {
            val navController = (context as AppCompatActivity).findNavController(
                R.id.nav_host_fragment)
            navController.navigate(NotesDirections.actionNotesToMakeNote(data))

        }
    }
}
class NotesDiffUtil() : DiffUtil.ItemCallback<NotesEntity>(){
    override fun areItemsTheSame(oldItem: NotesEntity, newItem: NotesEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NotesEntity, newItem: NotesEntity): Boolean {
        return oldItem == newItem
    }

}