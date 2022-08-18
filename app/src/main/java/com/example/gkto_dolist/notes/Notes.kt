package com.example.gkto_dolist.notes

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController

import com.example.gkto_dolist.utils.NoteDatabase
import com.example.gkto_dolist.utils.NotesAdapter
import com.example.gkto_dolist.databinding.FragmentNotesBinding

class Notes : Fragment() {
    //A context from the fragment lifecycle
    lateinit var mContext: Context
    //Data binding
    lateinit var binding: FragmentNotesBinding
    //RoomDatabase
    lateinit var room : NoteDatabase
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    //ViewModel object
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Binding initialization
        binding = FragmentNotesBinding.inflate(inflater)
        //Room initialization
        room = NoteDatabase.getDatabase(mContext)
        initViews()
        clicksfun()
        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //ViewModel initialization
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun initViews(){
        (mContext as AppCompatActivity).setSupportActionBar(binding.toolbar)
        val adapter = NotesAdapter(mContext)
        val data = room.notesDao.getAllNotes()
        data.observe(viewLifecycleOwner) { adapter.submitList(it) }
        val recyclerView = binding.notesrecyclerview
        recyclerView.adapter = adapter


    }
    fun clicksfun(){
        binding.addnote.setOnClickListener {
            findNavController().navigate(NotesDirections.actionNotesToMakeNote(null))
        }
    }

}