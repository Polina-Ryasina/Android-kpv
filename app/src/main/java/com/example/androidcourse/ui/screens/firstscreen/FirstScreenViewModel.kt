package com.example.androidcourse.ui.screens.firstscreen

import android.content.Context
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.androidcourse.R
import com.example.androidcourse.data.OptionsEnum
import com.example.androidcourse.helpers.sendNotification

class FirstScreenViewModel(val context: Context) : ViewModel() {

    var title by mutableStateOf("")
        private set

    var titleError by mutableStateOf<String?>(null)
        private set

    var body by mutableStateOf("")
        private set

    var expandIfLong by mutableStateOf(false)
        private set

    var openMainOnClick by mutableStateOf(true)
        private set

    var addReply by mutableStateOf(false)
        private set

    var selectedPriority by mutableStateOf(OptionsEnum.Medium)
        private set

    val hasNotificationPermission: Boolean
        get() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    fun onTitleChange(newTitle: String, fromCreate: Boolean = false) {
        if (!fromCreate) {
            titleError = if (newTitle.isNotBlank()) null else context.getString(R.string.title_error)
        }
        title = newTitle
    }

    fun onBodyChange(newBody: String) {
        body = newBody
        if (newBody.isBlank()) expandIfLong = false
    }

    fun onExpandChange(enabled: Boolean) {
        if (body.isNotBlank()) expandIfLong = enabled
    }

    fun onOpenMainChange(enabled: Boolean) {
        openMainOnClick = enabled
    }

    fun onAddReplyChange(enabled: Boolean) {
        addReply = enabled
    }

    fun onPriorityChange(priority: OptionsEnum) {
        selectedPriority = priority
    }

    fun createNotification() {
        if (title.isBlank()) {
            titleError = context.getString(R.string.title_error)
            return
        }

        if (!hasNotificationPermission) {
            return
        }
        sendNotification(
            context = context.applicationContext,
            title = title,
            body = if (body.isBlank()) null else body,
            expandIfLong = expandIfLong,
            openMainOnClick = openMainOnClick,
            addReply = addReply,
            importanceOption = selectedPriority
        )
    }
}
