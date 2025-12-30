package com.example.room.screens.register

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import com.example.room.R
import androidx.lifecycle.viewModelScope
import com.example.room.data.UserRepository
import com.example.room.db.entity.UserEntity
import kotlinx.coroutines.launch
import java.util.*

class RegisterViewModel(application: Application, private val repository: UserRepository) : AndroidViewModel(application) {

    private val context = application

    var email by mutableStateOf("")
        private set

    var nickname by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var registrationSuccess by mutableStateOf(false)
        private set


    fun onEmailChange(newEmail: String) {
        email = newEmail
        errorMessage = null
    }

    fun onNicknameChange(newNickname: String) {
        nickname = newNickname
        errorMessage = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        errorMessage = null
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword = newConfirmPassword
        errorMessage = null
    }

    fun registerUser() {
        if (email.isBlank() || nickname.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            errorMessage = context.getString(R.string.error_required_fields)
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = context.getString(R.string.error_invalid_email)
            return
        }

        if (password.length < 8) {
            errorMessage = context.getString(R.string.error_password_short)
            return
        }

        if (password != confirmPassword) {
            errorMessage = context.getString(R.string.error_password_mismatch)
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {

                val existingUser = repository.getUserByEmail(email)
                if (existingUser != null) {
                    errorMessage = context.getString(R.string.error_email_exists)
                    return@launch
                }

                val user = UserEntity(
                    email = email,
                    nickname = nickname,
                    passwordHash = password.hashCode().toString(),
                    createdAt = Date().time
                )
                repository.registerUser(user)
                registrationSuccess = true

            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}