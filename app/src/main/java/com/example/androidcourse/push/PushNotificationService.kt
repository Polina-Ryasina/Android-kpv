package com.example.androidcourse.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val data = message.data
        if (data.isEmpty()) return

        Log.d("FCM_PUSH", "Получен пуш: ${message.data}")

        val kind = data["kind"] ?: "default"
        val title = data["title"] ?: ""
        val body = data["message"] ?: ""

        NotificationHelper(this).show(kind, title, body)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM_TOKEN", token)
    }
}