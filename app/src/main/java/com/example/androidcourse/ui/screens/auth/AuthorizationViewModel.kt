package com.example.androidcourse.ui.screens.auth

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.androidcourse.R

class AuthorizationViewModel : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var emailInvalid by mutableStateOf(0)
        private set

    var passwordInvalid by mutableStateOf(0)
        private set

    var passwordIsVisible by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailInvalid = 0
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordInvalid = 0
    }

    fun onTogglePasswordVisibility() {
        passwordIsVisible = !passwordIsVisible
    }

    fun allFieldsCorrect(): Boolean {
        var isCorrect = true

        emailInvalid = 0
        passwordInvalid = 0

        if (email.isBlank()) {
            emailInvalid = R.string.blankError
            isCorrect = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInvalid = R.string.emailError
            isCorrect = false
        }

        if (password.isBlank()) {
            passwordInvalid = R.string.blankError
            isCorrect = false
        } else if (password.length < 8) {
            passwordInvalid = R.string.passwordError
            isCorrect = false
        }

        return isCorrect
    }

}