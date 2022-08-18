package com.example.gkto_dolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.gkto_dolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    lateinit var binding : ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

    }
}