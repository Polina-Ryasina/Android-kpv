package com.example.room.screens.profile

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.data.CharacterRepository
import com.example.room.data.UserRepository
import com.example.room.utils.SessionManager
import kotlinx.coroutines.launch
import com.example.room.db.entity.CharacterEntity
import com.example.room.db.entity.UserEntity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.room.R

class ProfileViewModel(
    application: Application,
    private val userRepository: UserRepository,
    private val characterRepository: CharacterRepository
) : AndroidViewModel(application) {

    private val context = application

    var user by mutableStateOf<UserEntity?>(null)
        private set

    var characters by mutableStateOf<List<CharacterEntity>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var pendingDelete by mutableStateOf(false)
        private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        val userId = SessionManager.currentUserId
        if (userId == null) {
            errorMessage = context.getString(R.string.not_login_user)
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                user = userRepository.getUserById(userId)
                characters = characterRepository.getCharactersByUser(userId)
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun deleteAccount() {
        pendingDelete = true
    }

    fun cancelDelete() {
        pendingDelete = false
    }

    fun confirmDeleteAccount() {
        val userId = SessionManager.currentUserId ?: return
        viewModelScope.launch {
            try {
                userRepository.updateDeleteDate(userId)
                SessionManager.logout()
                pendingDelete = false
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
                pendingDelete = false
            }
        }
    }
}