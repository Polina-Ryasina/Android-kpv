package com.example.androidcourse.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.androidcourse.data.Constants

class DeleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(Constants.EXTRA_NOTIFICATION_ID, -1)
        if (id != -1) {
            NotificationStorage.notifs.remove(id)
        }
    }
}