package com.example.androidcourse.helpers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import com.example.androidcourse.R
import com.example.androidcourse.data.Constants

object MessageStorage {
    val messages = mutableStateListOf<String>()
}

class ReplyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        val replyText = remoteInput?.getCharSequence(Constants.KEY_REPLY_TEXT)?.toString()

        if (!replyText.isNullOrEmpty()) {
            MessageStorage.messages.add(replyText)
            Toast.makeText(
                context,
                context.getString(R.string.reply_saved),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.reply_empty),
                Toast.LENGTH_SHORT
            ).show()
        }

        val notificationId = intent.getIntExtra(Constants.EXTRA_NOTIFICATION_ID, -1)
        if (notificationId != -1) {
            NotificationManagerCompat.from(context).cancel(notificationId)
        }
    }
}
