package com.example.androidcourse.ui.screens.secondscreen

import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidcourse.R
import com.example.androidcourse.helpers.NotificationStorage
import com.example.androidcourse.helpers.sendNotification

class SecondScreenViewModel : ViewModel() {

    var notificationId by mutableStateOf("")
        private set

    var notificationIdError by mutableStateOf<String?>(null)

    var newBody by mutableStateOf("")
        private set

    fun onNotificationIdChange(newId: String) {
        notificationId = newId
        notificationIdError = if (newId.isNotBlank()) null else null
    }

    fun onNewBodyChange(body: String) {
        newBody = body
    }

    fun updateNotification(context: Context) {
        if (notificationId.isBlank()) {
            notificationIdError = context.getString(R.string.id_error)
            return
        }
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = notificationId.toIntOrNull()
        if (id == null) {
            Toast.makeText(context, context.getString(R.string.invalid_id), Toast.LENGTH_SHORT).show()
            return
        }

        val notif = NotificationStorage.notifs[id]
        if (notif == null) {
            Toast.makeText(context, context.getString(R.string.notification_not_found), Toast.LENGTH_SHORT).show()
            return
        }

        try {
            sendNotification(
                context = context,
                notif.title,
                newBody,
                notif.expandIfLong,
                notif.openMainOnClick,
                notif.addReply,
                notif.importanceOption,
                id
                )
            Toast.makeText(context, context.getString(R.string.notification_updated), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, context.getString(R.string.notification_not_found), Toast.LENGTH_SHORT).show()
        }
    }

    fun clearAllNotifications(context: Context) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (NotificationStorage.notifs.isNotEmpty()) {
            for (id in NotificationStorage.notifs.keys.toList()) {
                manager.cancel(id)
                NotificationStorage.notifs.remove(id)
            }
            Toast.makeText(context, context.getString(R.string.clear), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, context.getString(R.string.nothing_to_clear), Toast.LENGTH_SHORT).show()
        }
    }
}
