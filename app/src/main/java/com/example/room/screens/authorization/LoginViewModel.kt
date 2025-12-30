package com.example.room.screens.authorization

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.*
import com.example.room.R
import com.example.room.data.UserRepository
import com.example.room.db.entity.UserEntity
import com.example.room.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(application: Application, private val repository: UserRepository) :
    AndroidViewModel(application) {

    private val context = application

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var pendingDeletedUser by mutableStateOf<UserEntity?>(null)

    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun loginUser() {
        if (email.isBlank() || password.isBlank()) {
            errorMessage = context.getString(R.string.error_required_fields)
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = context.getString(R.string.error_invalid_email)
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val user = repository.getUserByEmail(email)

                if (user == null || user.passwordHash != password.hashCode().toString()) {
                    errorMessage = context.getString(R.string.error_invalid_pair)
                } else if (user.deletedAt != null && repository.isDeletedRecently(user)) {
                    pendingDeletedUser = user
                } else if (user.deletedAt != null) {
                    errorMessage = context.getString(R.string.error_invalid_pair)
                } else {
                    SessionManager.login(user.id)
                    loginSuccess = true
                }

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}