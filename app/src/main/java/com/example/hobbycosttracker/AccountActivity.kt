package com.example.hobbycosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hobbycosttracker.R
import com.example.hobbycosttracker.session.SessionManager

class AccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val session = SessionManager(this)

        findViewById<TextView>(R.id.txtAccountEmailValue).text = session.getEmail()

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            session.logout()
            val i = Intent(this, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            finish()
        }
    }
}