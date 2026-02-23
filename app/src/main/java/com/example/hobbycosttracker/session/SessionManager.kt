package com.example.hobbycosttracker.session

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun setUserLoggedIn(userId: Long, email: String) {
        prefs.edit()
            .putLong("userId", userId)
            .putString("email", email)
            .apply()
    }

    fun getUserId(): Long {
        return prefs.getLong("userId", -1)
    }

    fun getEmail(): String {
        return prefs.getString("email", "") ?: ""
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}