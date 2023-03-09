package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityHomeBinding
import com.example.myapplication.model.data.ApplicationDatabase
import com.example.myapplication.model.data.LoggedEntity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = binding.user
        val next = binding.next
        val off = binding.off

        val intent = intent

        user.text = intent.getStringExtra("user")

        val userLogged = ApplicationDatabase.getAppDatabase(applicationContext!!)
            ?.loginDAO()?.getLogin()
        off.setOnClickListener {
            if (userLogged != null) {
                ApplicationDatabase.getAppDatabase(applicationContext!!)
                    ?.loginDAO()?.deleteLogin(LoggedEntity(
                        userId = userLogged.userId,
                        userName = userLogged.userName
                    ))
            }

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        next.setOnClickListener {
            val intentDebts = Intent(this, DebtsActivity::class.java)
            startActivity(intentDebts)
        }
    }
}