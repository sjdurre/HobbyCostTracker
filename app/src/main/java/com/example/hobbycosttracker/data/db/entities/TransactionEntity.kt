package com.example.hobbycosttracker.data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = HobbyEntity::class,
            parentColumns = ["id"],
            childColumns = ["hobbyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("hobbyId")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val hobbyId: Long,
    val type: String, // "COST" or "REVENUE"
    val amount: Double,
    val date: Long = System.currentTimeMillis(),
    val note: String? = null
)