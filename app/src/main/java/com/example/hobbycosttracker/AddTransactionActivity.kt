package com.example.hobbycosttracker

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hobbycosttracker.data.db.AppDatabase
import com.example.hobbycosttracker.data.db.entities.TransactionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var hobbyId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        db = AppDatabase.getInstance(this)

        // Get hobbyId from HomeActivity
        hobbyId = intent.getLongExtra("hobbyId", -1)
        if (hobbyId <= 0) {
            Toast.makeText(this, "No hobby selected.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val txtSelectedHobbyName = findViewById<TextView>(R.id.txtSelectedHobbyName)

        val radCost = findViewById<RadioButton>(R.id.radCost)
        val radRevenue = findViewById<RadioButton>(R.id.radRevenue)

        val edtAmount = findViewById<EditText>(R.id.edtAmount)
        val edtNote = findViewById<EditText>(R.id.edtNote)

        val btnSave = findViewById<Button>(R.id.btnSaveEntry)
        val btnCancel = findViewById<Button>(R.id.btnCancelEntry)

        // Default to COST
        radCost.isChecked = true

        // Show hobby name at top (optional but nice)
        lifecycleScope.launch {
            val name = withContext(Dispatchers.IO) {
                db.hobbyDao().getHobbyName(hobbyId)
            }
            txtSelectedHobbyName.text = "Selected Hobby: ${name ?: "Unknown"}"
        }

        btnSave.setOnClickListener {
            val amtText = edtAmount.text.toString().trim()
            val amount = amtText.toDoubleOrNull()

            if (amount == null || amount <= 0) {
                Toast.makeText(this, "Enter a valid amount.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = if (radRevenue.isChecked) "REVENUE" else "COST"
            val note = edtNote.text.toString().trim().ifEmpty { null }

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    db.transactionDao().insertTransaction(
                        TransactionEntity(
                            hobbyId = hobbyId,
                            type = type,
                            amount = amount,
                            note = note
                        )
                    )
                }
                finish() // back to Home (Home refreshes in onResume)
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }
}