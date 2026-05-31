package com.example.androidcourse.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.androidcourse.R

class NotificationHelper(private val context: Context) {

    companion object {
        const val CHANNEL_PROMO = "channel_promo"
        const val CHANNEL_AUTH = "channel_auth"
        const val CHANNEL_DEFAULT = "channel_default"
    }

    fun show(kind: String, title: String, message: String) {
        val channelId = getChannelId(kind)
        createChannelIfNeeded(channelId, kind)

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(getIcon(kind))
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(getPriority(kind))
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(kind.hashCode(), notification)
    }

    private fun getChannelId(kind: String) = when (kind) {
        "promo" -> CHANNEL_PROMO
        "auth" -> CHANNEL_AUTH
        else -> CHANNEL_DEFAULT
    }

    private fun getIcon(kind: String) = when (kind) {
        "promo" -> R.drawable.ic_launcher_foreground
        "auth" -> R.drawable.ic_launcher_foreground
        else -> R.drawable.ic_launcher_foreground
    }

    private fun getPriority(kind: String) = when (kind) {
        "auth" -> NotificationCompat.PRIORITY_HIGH
        "promo" -> NotificationCompat.PRIORITY_DEFAULT
        else -> NotificationCompat.PRIORITY_LOW
    }

    private fun createChannelIfNeeded(channelId: String, kind: String) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (manager.getNotificationChannel(channelId) != null) return

        val (name, importance) = when (kind) {
            "promo" -> context.getString(R.string.channel_promo_name) to NotificationManager.IMPORTANCE_DEFAULT
            "auth" -> context.getString(R.string.channel_auth_name) to NotificationManager.IMPORTANCE_HIGH
            else -> context.getString(R.string.channel_default_name) to NotificationManager.IMPORTANCE_LOW
        }

        val channel = NotificationChannel(channelId, name, importance)
        manager.createNotificationChannel(channel)
    }
}