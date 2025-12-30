package com.example.room.screens.addItem

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.R
import com.example.room.data.CharacterRepository
import com.example.room.db.entity.CharacterEntity
import com.example.room.utils.SessionManager
import kotlinx.coroutines.launch
import java.util.Date
import com.example.room.model.RaceOption
import com.example.room.model.ClassOption

class AddItemViewModel(application: Application, private val repository: CharacterRepository) : AndroidViewModel(application) {

    private val context = application.applicationContext

    var name by mutableStateOf("")
        private set

    var race by mutableStateOf<RaceOption?>(null)
        private set

    var characterClass by mutableStateOf<ClassOption?>(null)
        private set

    var level by mutableStateOf(context.getString(R.string.default_level_value))
        private set

    var strength by mutableStateOf(context.getString(R.string.default_parameter_value))
        private set

    var agility by mutableStateOf(context.getString(R.string.default_parameter_value))
        private set

    var intelligence by mutableStateOf(context.getString(R.string.default_parameter_value))
        private set

    var description by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var addSuccess by mutableStateOf(false)
        private set

    fun onNameChange(newName: String) {
        name = newName
        errorMessage = null
    }

    fun onRaceChange(newRace: RaceOption) {
        race = newRace
        errorMessage = null
    }

    fun onClassChange(newClass: ClassOption) {
        characterClass = newClass
        errorMessage = null
    }

    fun onLevelChange(newLevel: String) {
        level = newLevel
        errorMessage = null
    }

    fun onStrengthChange(newStrength: String) {
        strength = newStrength
        errorMessage = null
    }

    fun onAgilityChange(newAgility: String) {
        agility = newAgility
        errorMessage = null
    }

    fun onIntelligenceChange(newIntelligence: String) {
        intelligence = newIntelligence
        errorMessage = null
    }

    fun onDescriptionChange(newDescription: String) {
        description = newDescription
        errorMessage = null
    }

    fun addCharacter() {
        if (name.isBlank() || race == null || characterClass == null || level.isBlank() || strength.isBlank() || agility.isBlank() || intelligence.isBlank()) {
            errorMessage = context.getString(R.string.error_fill_all_required_fields)
            return
        }

        val currentUserId = SessionManager.currentUserId

        val levelInt = level.toIntOrNull()
        val strengthInt = strength.toIntOrNull() ?: 0
        val agilityInt = agility.toIntOrNull() ?: 0
        val intelligenceInt = intelligence.toIntOrNull() ?: 0

        if (levelInt == null) {
            errorMessage = context.getString(R.string.error_level_must_be_number)
            return
        }

        isLoading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val character = CharacterEntity(
                    id = 0,
                    userId = currentUserId,
                    name = name,
                    race = race!!.name,
                    characterClass = characterClass!!.name,
                    level = levelInt,
                    strength = strengthInt,
                    agility = agilityInt,
                    intelligence = intelligenceInt,
                    description = if (description.isBlank()) null else description,
                    createdAt = Date().time
                )
                repository.addCharacter(character)
                addSuccess = true
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }
}