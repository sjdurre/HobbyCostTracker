package com.example.hobbycosttracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hobbycosttracker.data.db.HobbySummary
import com.example.hobbycosttracker.data.db.entities.HobbyEntity

@Dao
interface HobbyDao {

    @Insert
    suspend fun insertHobby(hobby: HobbyEntity): Long

    @Query("""
        SELECT h.id AS hobbyId,
               h.name AS name,
               COALESCE(SUM(CASE WHEN t.type = 'COST' THEN t.amount END), 0) AS totalCost,
               COALESCE(SUM(CASE WHEN t.type = 'REVENUE' THEN t.amount END), 0) AS totalRevenue
        FROM hobbies h
        LEFT JOIN transactions t ON t.hobbyId = h.id
        WHERE h.userId = :userId
        GROUP BY h.id
        ORDER BY h.createdAt DESC
    """)
    suspend fun getHobbySummaries(userId: Long): List<HobbySummary>

    @Query("SELECT name FROM hobbies WHERE id = :hobbyId LIMIT 1")
    suspend fun getHobbyName(hobbyId: Long): String?
}