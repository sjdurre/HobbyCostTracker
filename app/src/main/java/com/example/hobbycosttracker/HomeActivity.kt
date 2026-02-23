package com.example.hobbycosttracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbycosttracker.adapters.HobbyAdapter
import com.example.hobbycosttracker.data.db.AppDatabase
import com.example.hobbycosttracker.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var adapter: HobbyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val session = SessionManager(this)
        if (session.getUserId() <= 0) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_home)

        db = AppDatabase.getInstance(this)

        findViewById<Button>(R.id.btnAddHobby).setOnClickListener {
            startActivity(Intent(this, AddHobbyActivity::class.java))
        }

        findViewById<Button>(R.id.btnAccount).setOnClickListener {
            startActivity(Intent(this, AccountActivity::class.java))
        }

        val rv = findViewById<RecyclerView>(R.id.rvHobbies)
        rv.layoutManager = LinearLayoutManager(this)

        adapter = HobbyAdapter(emptyList()) { hobby ->
            val i = Intent(this, AddTransactionActivity::class.java)
            i.putExtra("hobbyId", hobby.hobbyId)
            startActivity(i)
        }
        rv.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadHobbies()
    }

    private fun loadHobbies() {
        val userId = SessionManager(this).getUserId()

        lifecycleScope.launch {
            val list = withContext(Dispatchers.IO) {
                db.hobbyDao().getHobbySummaries(userId)
            }
            adapter.update(list)
        }
    }
}