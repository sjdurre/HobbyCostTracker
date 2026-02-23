package com.example.hobbycosttracker.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hobbycosttracker.data.db.dao.HobbyDao
import com.example.hobbycosttracker.data.db.dao.TransactionDao
import com.example.hobbycosttracker.data.db.entities.HobbyEntity
import com.example.hobbycosttracker.data.db.entities.TransactionEntity

@Database(
    entities = [HobbyEntity::class, TransactionEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun hobbyDao(): HobbyDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hobby_cost_tracker_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}