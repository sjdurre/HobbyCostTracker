package com.example.hobbycosttracker.data.db

data class HobbySummary(
    val hobbyId: Long,
    val name: String,
    val totalCost: Double,
    val totalRevenue: Double
) {
    val net: Double
        get() = totalRevenue - totalCost
}