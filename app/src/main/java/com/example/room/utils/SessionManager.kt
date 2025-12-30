package com.example.room.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SessionManager {

    private val PREFS_NAME = "user_prefs"
    private val KEY_LOGGED_IN = "is_logged_in"
    private const val KEY_USER_ID = "user_id"
    private lateinit var prefs: SharedPreferences

    var isLoggedIn by mutableStateOf(false)
        private set

    var currentUserId by mutableStateOf(-1)
        private set


    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        isLoggedIn = prefs.getBoolean(KEY_LOGGED_IN, false)
        currentUserId = prefs.getInt(KEY_USER_ID, -1)
    }

    fun login(userId: Int) {
        prefs.edit().putBoolean(KEY_LOGGED_IN, true).apply()
        prefs.edit().putInt(KEY_USER_ID, userId).apply()
        currentUserId = userId
        isLoggedIn = true
    }

    fun logout() {
        prefs.edit().putBoolean(KEY_LOGGED_IN, false).apply()
        prefs.edit().putInt(KEY_USER_ID, -1).apply()
        isLoggedIn = false
        currentUserId = -1
    }
}