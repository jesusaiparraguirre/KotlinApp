package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityDebtsBinding
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.viewmodel.DebtsViewModel

class DebtsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDebtsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDebtsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
 }