package com.example.androidcourse

import android.app.Application
import android.util.Log
import com.example.androidcourse.di.appModule
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.UUID

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            Log.d("FCM_TOKEN", "Token: $token")
        }

        initUserId()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }

    private fun initUserId() {
        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val userId = prefs.getString("user_id", null) ?: run {
            val newId = UUID.randomUUID().toString()
            prefs.edit().putString("user_id", newId).apply()
            newId
        }
        Firebase.crashlytics.setUserId(userId)
        Firebase.crashlytics.setCustomKey("user_id", userId)
    }
}