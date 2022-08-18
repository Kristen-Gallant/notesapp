package com.example.gkto_dolist.makenote

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.navArgs
import com.example.gkto_dolist.R
import com.example.gkto_dolist.utils.NoteDatabase
import com.example.gkto_dolist.utils.NotesEntity
import com.example.gkto_dolist.databinding.FragmentMakeNoteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MakeNote : Fragment() {
    //Data binding
    private lateinit var binding : FragmentMakeNoteBinding
    //A context from the fragment lifecycle
    private lateinit var mContext : Context
    //Coroutine scope
    private val uiScope = CoroutineScope(Dispatchers.Main)
    //Room Database object
    lateinit var room : NoteDatabase
    lateinit var sharedPreferences: SharedPreferences
    var noteCharNumbers : Int? = 0
    var subjectCharNumbers : Int? = 0
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMakeNoteBinding.inflate(inflater)
        room = NoteDatabase.getDatabase(mContext)
        initViews()
        fillForEdit()
        sharedPreferences = requireActivity().getSharedPreferences("user" , Context.MODE_PRIVATE)
        val args : MakeNoteArgs by navArgs()
        binding.lastEdited.text = "Last edited ${args.data?.date_and_time}"
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val args : MakeNoteArgs by navArgs()
        noteCharNumbers = args.data?.note?.length
        subjectCharNumbers = args.data?.subject?.length
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun fillForEdit() {
        val args : MakeNoteArgs by navArgs()
//        binding.subject.text = Editable.Factory.getInstance().newEditable(args.data?.subject)
//        binding.note.text = Editable.Factory.getInstance().newEditable(args.data?.note)
        binding.subject.setText(args.data?.subject)
        binding.note.setText(args.data?.note)
    }

    fun initViews(){
        val activity = (mContext as AppCompatActivity)
        setHasOptionsMenu(true)
        activity.setSupportActionBar(binding.toolbar)
        activity.actionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.navigationIcon = AppCompatResources.getDrawable(mContext , R.drawable.ic_baseline_arrow_back_24)
    }

    override fun onPause() {
        super.onPause()
        uiScope.launch {
            //this checks if the note exists
            val args : MakeNoteArgs by navArgs()
            val checkIfExist = args.data?.id?.let { room.notesDao.checkNote(it)}
          if (checkIfExist == 1){
              val simpleDateFormat =  SimpleDateFormat("yyyy.MMMM.dd hh:mm:ss aaa",
                  Locale.getDefault())
              val currentDateAndTime: String = simpleDateFormat.format(Date())
              room.notesDao.updateNote(binding.subject.text.toString(),
                  binding.note.text.toString() , args.data!!.secondaryId , currentDateAndTime)
              Toast.makeText(mContext , "q1" , Toast.LENGTH_SHORT).show()
          }else if (binding.note.text.isNotEmpty() && binding.subject.text.isNotEmpty()){
              val deviceName = android.os.Build.MODEL
              val randomString = UUID.randomUUID().toString().substring(0,5)
              val userName = sharedPreferences.getString("username" ,"")
              Toast.makeText(mContext , "q2" , Toast.LENGTH_SHORT).show()
              val simpleDateFormat =  SimpleDateFormat("yyyy.MMMM.dd hh:mm:ss aaa", Locale.getDefault())
              val currentDateAndTime: String = simpleDateFormat.format(Date())
              val notedata = NotesEntity(0 , randomString, binding.subject.text.toString() , binding.note.text.toString() , currentDateAndTime)
              room.notesDao.insertNote(notedata)
          }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notemenu , menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> requireActivity().onBackPressed()

        }
        return true
    }


}