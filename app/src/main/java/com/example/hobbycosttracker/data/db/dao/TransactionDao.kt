package com.example.hobbycosttracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hobbycosttracker.data.db.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE hobbyId = :hobbyId")
    suspend fun getTransactionsForHobby(hobbyId: Long): List<TransactionEntity>
}