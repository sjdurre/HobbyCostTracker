package com.example.hobbycosttracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hobbycosttracker.data.db.AppDatabase
import com.example.hobbycosttracker.data.db.entities.HobbyEntity
import com.example.hobbycosttracker.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddHobbyActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hobby)

        db = AppDatabase.getInstance(this)

        val edtHobbyName = findViewById<EditText>(R.id.edtHobbyName)
        val btnSave = findViewById<Button>(R.id.btnSaveHobby)
        val btnCancel = findViewById<Button>(R.id.btnCancelHobby)

        btnSave.setOnClickListener {
            val name = edtHobbyName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a hobby name.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = SessionManager(this).getUserId()

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    db.hobbyDao().insertHobby(
                        HobbyEntity(
                            userId = userId,
                            name = name
                        )
                    )
                }
                finish() // return to Home
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}