package com.example.androidcourse

import android.content.Context

object MainActivitiesController {
    private val activeMainActivities = mutableListOf<MainActivity>()

    fun addActivity(activity: MainActivity) {
        activeMainActivities.add(activity)
    }

    fun closeAll() {
        for (activity in activeMainActivities) {
            activity.finish()
        }
        activeMainActivities.clear()
    }
}