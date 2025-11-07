package com.example.androidcourse.helpers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import com.example.androidcourse.MainActivity
import com.example.androidcourse.R
import com.example.androidcourse.data.ChannelsIdsEnum
import com.example.androidcourse.data.ChannelsNamesEnum
import com.example.androidcourse.data.Constants
import com.example.androidcourse.data.OptionsEnum

data class Notif (
    var title: String,
    var body: String,
    var expandIfLong: Boolean,
    var openMainOnClick: Boolean,
    var addReply: Boolean,
    var importanceOption: OptionsEnum
)

object NotificationStorage {
    val notifs = mutableMapOf<Int, Notif>()
}

fun sendNotification(
    context: Context,
    title: String,
    body: String?,
    expandIfLong: Boolean,
    openMainOnClick: Boolean,
    addReply: Boolean,
    importanceOption: OptionsEnum,
    id: Int? = null
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = when (importanceOption) {
            OptionsEnum.Low -> NotificationManager.IMPORTANCE_LOW
            OptionsEnum.Medium -> NotificationManager.IMPORTANCE_DEFAULT
            OptionsEnum.High -> NotificationManager.IMPORTANCE_HIGH
            OptionsEnum.Urgent -> NotificationManager.IMPORTANCE_MAX
        }

        val channel = NotificationChannel(ChannelsIdsEnum.valueOf(importanceOption.name).name, ChannelsNamesEnum.valueOf(importanceOption.name).name, importance)
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    val notificationId = id ?: (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

    val pendingIntent = if (openMainOnClick) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(Constants.EXTRA_TITLE, title)
            putExtra(Constants.EXTRA_TEXT, body)
        }
        PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_IMMUTABLE)
    } else null

    val priority = when (importanceOption) {
        OptionsEnum.Low -> NotificationCompat.PRIORITY_LOW
        OptionsEnum.Medium -> NotificationCompat.PRIORITY_DEFAULT
        OptionsEnum.High -> NotificationCompat.PRIORITY_HIGH
        OptionsEnum.Urgent -> NotificationCompat.PRIORITY_MAX
    }

    val builder = NotificationCompat.Builder(context, ChannelsIdsEnum.valueOf(importanceOption.name).name)
        .setContentTitle(title)
        .setContentText(body)
        .setSmallIcon(R.drawable.notification)
        .setPriority(priority)
        .setAutoCancel(true)

    if (expandIfLong && !body.isNullOrEmpty()) {
        builder.setStyle(NotificationCompat.BigTextStyle().bigText(body))
    }

    if (pendingIntent != null) {
        builder.setContentIntent(pendingIntent)
    }

    if (addReply) {
        val remoteInput = RemoteInput.Builder(Constants.KEY_REPLY_TEXT)
            .setLabel(context.getString(R.string.reply_label))
            .build()

        val replyIntent = Intent(context, ReplyReceiver::class.java).apply {
            putExtra(Constants.EXTRA_NOTIFICATION_ID, notificationId)
        }

        val replyPendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )

        val action = NotificationCompat.Action.Builder(
            R.drawable.reply,
            context.getString(R.string.reply_action_text),
            replyPendingIntent
        ).addRemoteInput(remoteInput).build()

        builder.addAction(action)
    }

    val deleteIntent = Intent(context, DeleteReceiver::class.java).apply {
        putExtra(Constants.EXTRA_NOTIFICATION_ID, notificationId)
    }
    val deletePendingIntent = PendingIntent.getBroadcast(
        context,
        notificationId,
        deleteIntent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )
    builder.setDeleteIntent(deletePendingIntent)

    val manager = NotificationManagerCompat.from(context)
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    ) {
        manager.notify(notificationId, builder.build())
        NotificationStorage.notifs[notificationId] = Notif(title, body ?: "", expandIfLong, openMainOnClick, addReply, importanceOption)
    }
}
