package com.example.hobbycosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hobbycosttracker.HomeActivity
import com.example.hobbycosttracker.R
import com.example.hobbycosttracker.session.SessionManager

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val session = SessionManager(this)

        // If user already logged in, skip login screen
        if (session.getUserId() > 0) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val pass = edtPassword.text.toString().trim()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter email and password.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // MVP login: accept anything and store a session
            session.setUserLoggedIn(userId = 1L, email = email)

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}